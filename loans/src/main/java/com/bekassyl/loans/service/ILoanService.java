package com.bekassyl.loans.service;

import com.bekassyl.loans.dto.response.LoanDetailsResponseDto;
import com.bekassyl.loans.dto.request.LoanRequestDto;
import com.bekassyl.loans.dto.LoanDto;
import com.bekassyl.loans.exception.ResourceNotFoundException;

import java.util.List;

public interface ILoanService {
    /**
     * Finds the list of loans by book isbn.
     *
     * @param bookIsbn isbn to search for
     * @return DTO containing loans for the given book isbn
     * @throws ResourceNotFoundException if loans are not found
     */
    List<LoanDto> fetchLoansByBookIsbn(String bookIsbn);

    /**
     * Finds the list of loans by member card number.
     *
     * @param memberCardNumber card number to search for
     * @return List of DTOs containing loans for the given member card number
     * @throws ResourceNotFoundException if loans are not found
     */
    List<LoanDto> fetchLoansByMemberCardNumber(String memberCardNumber);

    /**
     * Saves the new loan.
     *
     * @param requestDto request loan data transfer object
     * @return LoanDetailsResponseDto loan details data transfer object
     * @throws ResourceNotFoundException if there are no books available
     */
    LoanDetailsResponseDto createLoan(LoanRequestDto requestDto);

    /**
     * Returns the book and updates the loan status to 'returned'.
     *
     * @param requestDto request loan data transfer object
     * @return LoanDetailsResponseDto loan details data transfer object
     * @throws ResourceNotFoundException if there are no books available
     */
    LoanDetailsResponseDto returnBook(LoanRequestDto requestDto);

    /**
     * Extends the book loan for another 7 days.
     *
     * @param requestDto request loan data transfer object
     * @return LoanDetailsResponseDto loan details data transfer object
     * @throws ResourceNotFoundException if there are no books available
     */
    LoanDetailsResponseDto extendLoan(LoanRequestDto requestDto);
}
