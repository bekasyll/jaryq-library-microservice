package com.bekassyl.loans.service.impl;

import com.bekassyl.loans.dto.LoanDto;
import com.bekassyl.loans.dto.LoanRequestDto;
import com.bekassyl.loans.entity.Loan;
import com.bekassyl.loans.mapper.LoanMapper;
import com.bekassyl.loans.repository.LoanRepository;
import com.bekassyl.loans.service.ILoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bekassyl.loans.exception.ResourceNotFoundException;
import com.bekassyl.loans.exception.BookAlreadyBorrowedException;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoanServiceImpl implements ILoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;

    /**
     * Finds loan details by book id.
     *
     * @param bookId id to search for
     * @return DTO containing loan details for the given book id
     * @throws ResourceNotFoundException if a loan is not found
     */
    @Override
    public LoanDto fetchLoanByBookId(Long bookId) {
        Loan loan = loanRepository.findByBookId(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "bookId", bookId.toString())
        );
        return loanMapper.toDto(loan);
    }

    /**
     * Finds the list of loan details by member id.
     *
     * @param memberId id to search for
     * @return List of DTOs containing loan details for the given member id
     * @throws ResourceNotFoundException if a loan is not found
     */
    @Override
    public List<LoanDto> fetchLoanByMemberId(Long memberId) {
        List<Loan> loans = loanRepository.findByMemberId(memberId);
        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("Loan", "memberId", memberId.toString());
        }

        return loanMapper.toDtoList(loans);
    }

    /**
     * Saves the new loan.
     *
     * @param requestDto request loan data transfer object
     * @throws BookAlreadyBorrowedException if a book with this identifier is already borrowed
     */
    @Transactional
    @Override
    public boolean createLoan(LoanRequestDto requestDto) {
        if (loanRepository.existsByBookIdAndStatusNot(requestDto.getBookId(), Loan.LoanStatus.RETURNED)) {
            throw new BookAlreadyBorrowedException("Book with ID " + requestDto.getBookId() + " already borrowed");
        }

        Loan loan = loanMapper.toEntityFromRequest(requestDto);

        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusDays(7));
        loan.setStatus(Loan.LoanStatus.BORROWED);

        loanRepository.save(loan);

        return true;
    }

    /**
     * Updates the loan status to 'returned'.
     *
     * @param id id to identify the loan
     * @return {@code true} if update was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a loan is not found
     */
    @Transactional
    @Override
    public boolean returnBook(Long id) {
        Loan loan = loanRepository.findByIdAndStatusNot(id, Loan.LoanStatus.RETURNED).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "id", id.toString())
        );

        loan.setReturnDate(LocalDate.now());
        loan.setStatus(Loan.LoanStatus.RETURNED);

        return true;
    }

    /**
     * Extends the book loan for another 7 days.
     *
     * @param id id to identify the loan
     * @return {@code true} if update was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a loan is not found
     */
    @Transactional
    @Override
    public boolean extendLoan(Long id) {
        Loan loan = loanRepository.findByIdAndStatusNot(id, Loan.LoanStatus.RETURNED).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "id", id.toString())
        );

        loan.setReturnDate(loan.getReturnDate().plusDays(7));
        loanRepository.save(loan);
        return true;
    }

    /**
     * Deletes the loan details.
     *
     * @param id id to identify the loan
     * @return {@code true} if delete was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a book is not found
     */
    @Transactional
    @Override
    public boolean deleteLoan(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "id", id.toString())
        );

        loanRepository.delete(loan);
        return true;
    }
}
