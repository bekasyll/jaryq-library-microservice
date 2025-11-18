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
public class LoanIdRequestDto {
    @Schema(description = "ID of the Loan", example = "10")
    @NotNull(message = "Loan ID is required")
    @Positive(message = "Loan ID must be a positive number")
    private Long id;
}
