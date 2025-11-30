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
@Transactional(readOnly = true)
public class LoanServiceImpl implements ILoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final BooksFeignClient booksFeignClient;
    private final MembersFeignClient membersFeignClient;

    /**
     * Finds the list of loans by book isbn.
     *
     * @param bookIsbn isbn to search for
     * @return DTO containing loans for the given book isbn
     * @throws ResourceNotFoundException if loans are not found
     */
    @Override
    public List<LoanDto> fetchLoansByBookIsbn(String bookIsbn) {
        List<Loan> loans = loanRepository.findByBookIsbn(bookIsbn);

        return getLoanDtos(loans);
    }

    /**
     * Finds the list of loans by member card number.
     *
     * @param memberCardNumber card number to search for
     * @return List of DTOs containing loans for the given member card number
     * @throws ResourceNotFoundException if loans are not found
     */
    @Override
    public List<LoanDto> fetchLoansByMemberCardNumber(String memberCardNumber) {
        List<Loan> loans = loanRepository.findByMemberCardNumber(memberCardNumber);

        return getLoanDtos(loans);
    }

    private List<LoanDto> getLoanDtos(List<Loan> loans) {
        List<LoanDto> loanDtos = new ArrayList<>();

        for (Loan loan : loans) {
            MemberDto memberDto = membersFeignClient.fetchMemberByCardNumber(loan.getMemberCardNumber()).getBody();
            BookDto bookDto = booksFeignClient.fetchBookDetails(loan.getBookIsbn()).getBody();

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
     * @return LoanDetailsResponseDto loan details data transfer object
     * @throws ResourceNotFoundException if there are no books available
     */
    @Transactional
    @Override
    public LoanDetailsResponseDto createLoan(LoanRequestDto requestDto) {
        boolean isBorrowed = loanRepository.existsByBookIsbnAndMemberCardNumberAndStatus(
                requestDto.getBookIsbn(), requestDto.getMemberCardNumber(), Loan.LoanStatus.BORROWED);

        if (isBorrowed) {
            throw new ResourceNotFoundException(
                    "Loan", "a member has already borrowed this book",
                    "book isbn: " + requestDto.getBookIsbn() + ", " + "member card number: " + requestDto.getMemberCardNumber()
            );
        }

        boolean loanBook = booksFeignClient.loanBook(requestDto.getBookIsbn());
        MemberDto memberDto = membersFeignClient.fetchMemberByCardNumber(requestDto.getMemberCardNumber()).getBody();

        Loan loan = new Loan();

        if (loanBook) {
            loan.setBookIsbn(requestDto.getBookIsbn());
            loan.setMemberCardNumber(memberDto.getCardNumber());
            loan.setLoanDate(LocalDate.now());
            loan.setReturnDate(LocalDate.now().plusDays(7));
            loan.setStatus(Loan.LoanStatus.BORROWED);

            loanRepository.save(loan);
        } else {
            throw new ResourceNotFoundException(
                    "Loan", "no available books",
                    "book isbn: " + requestDto.getBookIsbn() + ", " + "member card number: " + requestDto.getMemberCardNumber()
            );
        }

        return new LoanDetailsResponseDto(
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus(),
                membersFeignClient.fetchMemberByCardNumber(requestDto.getMemberCardNumber()).getBody(),
                booksFeignClient.fetchBookDetails(requestDto.getBookIsbn()).getBody()
        );
    }

    /**
     * Returns the book and updates the loan status to 'returned'.
     *
     * @param requestDto request loan data transfer object
     * @return LoanDetailsResponseDto loan details data transfer object
     * @throws ResourceNotFoundException if there are no books available
     */
    @Transactional
    @Override
    public LoanDetailsResponseDto returnBook(LoanRequestDto requestDto) {
        Loan loan = loanRepository.findByBookIsbnAndMemberCardNumberAndStatus(
                requestDto.getBookIsbn(), requestDto.getMemberCardNumber(), Loan.LoanStatus.BORROWED);

        if (loan != null) {
            loan.setReturnDate(LocalDate.now());
            loan.setStatus(Loan.LoanStatus.RETURNED);

            loanRepository.save(loan);

            booksFeignClient.returnBook(requestDto.getBookIsbn());
        } else {
            throw new ResourceNotFoundException(
                    "Loan", "no loan",
                    "book isbn: " + requestDto.getBookIsbn() + ", " + "member card number: " + requestDto.getMemberCardNumber()
            );
        }

        return new LoanDetailsResponseDto(
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus(),
                membersFeignClient.fetchMemberByCardNumber(requestDto.getMemberCardNumber()).getBody(),
                booksFeignClient.fetchBookDetails(requestDto.getBookIsbn()).getBody()
        );
    }

    /**
     * Extends the book loan for another 7 days.
     *
     * @param requestDto request loan data transfer object
     * @return LoanDetailsResponseDto loan details data transfer object
     * @throws ResourceNotFoundException if there are no books available
     */
    @Transactional
    @Override
    public LoanDetailsResponseDto extendLoan(LoanRequestDto requestDto) {
        Loan loan = loanRepository.findByBookIsbnAndMemberCardNumberAndStatus(
                requestDto.getBookIsbn(), requestDto.getMemberCardNumber(), Loan.LoanStatus.BORROWED);

        if (loan != null) {
            if (LocalDate.now().isEqual(loan.getReturnDate())) {
                loan.setReturnDate(loan.getReturnDate().plusDays(7));

                loanRepository.save(loan);
            } else {
                throw new ResourceNotFoundException(
                        "Loan", "a loan can be extended only on its due date",
                        "book isbn: " + requestDto.getBookIsbn() + ", " + "member card number: " + requestDto.getMemberCardNumber()
                );
            }
        }else {
            throw new ResourceNotFoundException(
                    "Loan", "no loan",
                    "book isbn: " + requestDto.getBookIsbn() + ", " + "member card number: " + requestDto.getMemberCardNumber()
            );
        }

        return new LoanDetailsResponseDto(
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus(),
                membersFeignClient.fetchMemberByCardNumber(requestDto.getMemberCardNumber()).getBody(),
                booksFeignClient.fetchBookDetails(requestDto.getBookIsbn()).getBody()
        );
    }
}
