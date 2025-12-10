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
     * @param correlationId request correlation id
     * @return DTO containing loans for the given book isbn
     * @throws ResourceNotFoundException if loans are not found
     */
    List<LoanDto> fetchLoansByBookIsbn(String bookIsbn, String correlationId);

    /**
     * Finds the list of loans by member iin.
     *
     * @param memberIin member iin to search for
     * @param correlationId request correlation id
     * @return List of DTOs containing loans for the given member iin
     * @throws ResourceNotFoundException if loans are not found
     */
    List<LoanDto> fetchLoansByMemberIin(String memberIin, String correlationId);

    /**
     * Saves the new loan.
     *
     * @param requestDto request loan data transfer object
     * @param correlationId request correlation id
     * @return LoanDetailsResponseDto loan details data transfer object
     * @throws ResourceNotFoundException if there are no books available
     */
    LoanDetailsResponseDto createLoan(LoanRequestDto requestDto, String correlationId);

    /**
     * Returns the book and updates the loan status to 'returned'.
     *
     * @param requestDto request loan data transfer object
     * @param correlationId request correlation id
     * @return LoanDetailsResponseDto loan details data transfer object
     * @throws ResourceNotFoundException if there are no books available
     */
    LoanDetailsResponseDto returnBook(LoanRequestDto requestDto, String correlationId);

    /**
     * Extends the book loan for another 7 days.
     *
     * @param requestDto request loan data transfer object
     * @param correlationId request correlation id
     * @return LoanDetailsResponseDto loan details data transfer object
     * @throws ResourceNotFoundException if there are no books available
     */
    LoanDetailsResponseDto extendLoan(LoanRequestDto requestDto, String correlationId);
}
