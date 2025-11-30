package com.bekassyl.loans.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Loan Request",
        description = "Schema to hold loan request (book id and member id) information"
)
public class LoanRequestDto {
    @Schema(description = "ISBN of the book", example = "9780141182636")
    @NotBlank(message = "ISBN cannot be blank")
    @Pattern(regexp = "\\d{13}", message = "ISBN must contain exactly 13 digits")
    private String bookIsbn;

    @Schema(description = "Card number of the member", example = "708409220539")
    @Size(max = 12, message = "Card number must not exceed 12 characters")
    private String memberCardNumber;
}