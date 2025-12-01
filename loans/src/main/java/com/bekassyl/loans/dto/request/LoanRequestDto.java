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

    @NotBlank(message = "IIN cannot be blank")
    @Size(max = 12, message = "IIN must not exceed 12 characters")
    @Schema(description = "IIN of the member", example = "180100586526")
    private String memberIin;
}