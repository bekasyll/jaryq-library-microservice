package com.bekassyl.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Loan Request",
        description = "Schema to hold loan request (book id and member id) information"
)
public class LoanRequestDto {
    @Schema(description = "Book ID of the book", example = "10")
    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be a positive number")
    private Long bookId;

    @Schema(description = "Member ID of the member", example = "4")
    @NotNull(message = "Member ID is required")
    @Positive(message = "Member ID must be a positive number")
    private Long memberId;
}