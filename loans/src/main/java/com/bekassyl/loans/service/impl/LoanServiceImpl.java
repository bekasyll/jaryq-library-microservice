package com.bekassyl.loans.service.impl;

import com.bekassyl.loans.dto.*;
import com.bekassyl.loans.dto.request.LoanRequestDto;
import com.bekassyl.loans.dto.response.LoanDetailsResponseDto;
import com.bekassyl.loans.entity.Loan;
import com.bekassyl.loans.mapper.LoanMapper;
import com.bekassyl.loans.repository.LoanRepository;
import com.bekassyl.loans.service.ILoanService;
import com.bekassyl.loans.service.client.BooksFeignClient;
import com.bekassyl.loans.service.client.MembersFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bekassyl.loans.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements ILoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final BooksFeignClient booksFeignClient;
    private final MembersFeignClient membersFeignClient;

    /**
     * Finds the list of loans by book isbn.
     *
     * @param bookIsbn isbn to search for
     * @param correlationId request correlation id
     * @return DTO containing loans for the given book isbn
     * @throws ResourceNotFoundException if loans are not found
     */
    @Override
    public List<LoanDto> fetchLoansByBookIsbn(String bookIsbn, String correlationId) {
        List<Loan> loans = loanRepository.findByBookIsbn(bookIsbn);

        return getLoanDtos(loans, correlationId);
    }

    /**
     * Finds the list of loans by member iin.
     *
     * @param memberIin member iin to search for
     * @param correlationId request correlation id
     * @return List of DTOs containing loans for the given member iin
     * @throws ResourceNotFoundException if loans are not found
     */
    @Override
    public List<LoanDto> fetchLoansByMemberIin(String memberIin, String correlationId) {
        List<Loan> loans = loanRepository.findByMemberIin(memberIin);

        return getLoanDtos(loans, correlationId);
    }

    private List<LoanDto> getLoanDtos(List<Loan> loans, String correlationId) {
        List<LoanDto> loanDtos = new ArrayList<>();

        for (Loan loan : loans) {
            MemberDto memberDto = membersFeignClient.fetchMemberByIin(loan.getMemberIin(), correlationId).getBody();
            BookDto bookDto = booksFeignClient.fetchBookDetails(loan.getBookIsbn(), correlationId).getBody();

            LoanDto loanDto = new LoanDto();
            loanDto.setId(loan.getId());
            loanDto.setTitle(bookDto.getTitle());
            loanDto.setAuthor(bookDto.getAuthor());
            loanDto.setIsbn(bookDto.getIsbn());
            loanDto.setCardNumber(memberDto.getCardNumber());
            loanDto.setFirstName(memberDto.getFirstName());
            loanDto.setLastName(memberDto.getLastName());
            loanDto.setIin(memberDto.getIin());
            loanDto.setLoanDate(loan.getLoanDate());
            loanDto.setReturnDate(loan.getReturnDate());
            loanDto.setStatus(loan.getStatus());

            loanDtos.add(loanDto);
        }

        return loanDtos;
    }

    /**
     * Saves the new loan.
     *
     * @param requestDto request loan data transfer object
     * @param correlationId request correlation id
     * @return LoanDetailsResponseDto loan details data transfer object
     * @throws ResourceNotFoundException if there are no books available
     */
    @Override
    public LoanDetailsResponseDto createLoan(LoanRequestDto requestDto, String correlationId) {
        boolean isBorrowed = loanRepository.existsByBookIsbnAndMemberIinAndStatus(
                requestDto.getBookIsbn(), requestDto.getMemberIin(), Loan.LoanStatus.BORROWED);

        if (isBorrowed) {
            throw new ResourceNotFoundException(
                    "Loan", "a member has already borrowed this book",
                    "book isbn: " + requestDto.getBookIsbn() + ", " + "member iin: " + requestDto.getMemberIin()
            );
        }

        Boolean loanBook = booksFeignClient.loanBook(requestDto.getBookIsbn(), correlationId);

        if (!Boolean.TRUE.equals(loanBook)) {
            throw new ResourceNotFoundException(
                    "Loan", "no available books",
                    "book isbn: " + requestDto.getBookIsbn() + ", " + "member iin: " + requestDto.getMemberIin()
            );
        }

        BookDto bookDto = booksFeignClient.fetchBookDetails(requestDto.getBookIsbn(), correlationId).getBody();
        MemberDto memberDto = membersFeignClient.fetchMemberByIin(requestDto.getMemberIin(), correlationId).getBody();

        Loan loan = saveLoanTransactional(memberDto, bookDto);

        return new LoanDetailsResponseDto(
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus(),
                memberDto,
                bookDto
        );
    }

    @Transactional
    protected Loan saveLoanTransactional(MemberDto memberDto, BookDto bookDto) {
        Loan loan = new Loan();

        loan.setBookIsbn(bookDto.getIsbn());
        loan.setMemberIin(memberDto.getIin());
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusDays(7));
        loan.setStatus(Loan.LoanStatus.BORROWED);

        return loanRepository.save(loan);
    }

    /**
     * Returns the book and updates the loan status to 'returned'.
     *
     * @param requestDto request loan data transfer object
     * @param correlationId request correlation id
     * @return LoanDetailsResponseDto loan details data transfer object
     * @throws ResourceNotFoundException if there are no books available
     */
    @Override
    public LoanDetailsResponseDto returnBook(LoanRequestDto requestDto, String correlationId) {
        Loan loan = returnBookTransactional(requestDto);

        booksFeignClient.returnBook(requestDto.getBookIsbn(), correlationId);

        return new LoanDetailsResponseDto(
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus(),
                membersFeignClient.fetchMemberByIin(requestDto.getMemberIin(), correlationId).getBody(),
                booksFeignClient.fetchBookDetails(requestDto.getBookIsbn(), correlationId).getBody()
        );
    }

    @Transactional
    protected Loan returnBookTransactional(LoanRequestDto requestDto) {
        Loan loan = loanRepository.findByBookIsbnAndMemberIinAndStatus(
                requestDto.getBookIsbn(), requestDto.getMemberIin(), Loan.LoanStatus.BORROWED);

        if (loan == null) {
            throw new ResourceNotFoundException(
                    "Loan", "no loan", "book isbn: " + requestDto.getBookIsbn() + ", " + "member iin: " + requestDto.getMemberIin()
            );
        }
        loan.setReturnDate(LocalDate.now());
        loan.setStatus(Loan.LoanStatus.RETURNED);

        return loanRepository.save(loan);
    }

    /**
     * Extends the book loan for another 7 days.
     *
     * @param requestDto request loan data transfer object
     * @param correlationId request correlation id
     * @return LoanDetailsResponseDto loan details data transfer object
     * @throws ResourceNotFoundException if there are no books available
     */
    @Override
    public LoanDetailsResponseDto extendLoan(LoanRequestDto requestDto, String correlationId) {
        Loan loan = extendLoanTransactional(requestDto);

        MemberDto memberDto = membersFeignClient.fetchMemberByIin(requestDto.getMemberIin(), correlationId).getBody();
        BookDto bookDto = booksFeignClient.fetchBookDetails(requestDto.getBookIsbn(), correlationId).getBody();

        return new LoanDetailsResponseDto(
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus(),
                memberDto,
                bookDto
        );
    }

    @Transactional
    protected Loan extendLoanTransactional(LoanRequestDto requestDto) {
        Loan loan = loanRepository.findByBookIsbnAndMemberIinAndStatus(
                requestDto.getBookIsbn(), requestDto.getMemberIin(), Loan.LoanStatus.BORROWED);

        if (loan == null) {
            throw new ResourceNotFoundException(
                    "Loan", "no loan", "book isbn: " + requestDto.getBookIsbn() + ", " + "member iin: " + requestDto.getMemberIin()
            );
        }

        if (!LocalDate.now().isEqual(loan.getReturnDate())) {
            throw new ResourceNotFoundException(
                    "Loan", "a loan can be extended only on its due date", "book isbn: " + requestDto.getBookIsbn() + ", " + "member iin: " + requestDto.getMemberIin()
            );
        }

        loan.setReturnDate(loan.getReturnDate().plusDays(7));

        return loanRepository.save(loan);
    }
}
