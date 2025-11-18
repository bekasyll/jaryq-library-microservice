package com.bekassyl.loans.service;

import com.bekassyl.loans.dto.LoanRequestDto;
import com.bekassyl.loans.dto.LoanDto;
import com.bekassyl.loans.exception.ResourceNotFoundException;
import com.bekassyl.loans.exception.BookAlreadyBorrowedException;
import java.util.List;

public interface ILoanService {
    /**
     * Finds loan details by book id.
     *
     * @param bookId id to search for
     * @return DTO containing loan details for the given book id
     * @throws ResourceNotFoundException if a loan is not found
     */
    LoanDto fetchLoanByBookId(Long bookId);

    /**
     * Finds the list of loan details by member id.
     *
     * @param memberId id to search for
     * @return List of DTOs containing loan details for the given member id
     * @throws ResourceNotFoundException if a loan is not found
     */
    List<LoanDto> fetchLoanByMemberId(Long memberId);

    /**
     * Saves the new loan.
     *
     * @param requestDto request loan data transfer object
     * @return {@code true} if update was successful, {@code false} otherwise
     * @throws BookAlreadyBorrowedException if a book with this identifier is already borrowed
     */
    boolean createLoan(LoanRequestDto requestDto);

    /**
     * Updates the loan status to 'returned'.
     *
     * @param id id to identify the loan
     * @return {@code true} if update was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a loan is not found
     */
    boolean returnBook(Long id);

    /**
     * Extends the book loan for another 7 days.
     *
     * @param id id to identify the loan
     * @return {@code true} if update was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a loan is not found
     */
    boolean extendLoan(Long id);

    /**
     * Deletes the loan details.
     *
     * @param id id to identify the loan
     * @return {@code true} if delete was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a book is not found
     */
    boolean deleteLoan(Long id);
}
