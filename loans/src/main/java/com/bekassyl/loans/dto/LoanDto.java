package com.bekassyl.loans.dto;

import com.bekassyl.loans.entity.Loan;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Schema(
        name = "Loan",
        description = "Schema to hold Loan information"
)
public class LoanDto {
    @NotNull(message = "Loan ID is required")
    @Positive(message = "Loan ID must be a positive number")
    @Schema(description = "Loan ID", example = "1")
    private Long id;

    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be a positive number")
    @Schema(description = "Book ID of the book", example = "10")
    private Long bookId;

    @NotNull(message = "Member ID is required")
    @Positive(message = "Member ID must be a positive number")
    @Schema(description = "Member ID of the member", example = "4")
    private Long memberId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Schema(description = "Loan date", example = "01-01-2020")
    private LocalDate loanDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Schema(description = "Return date", example = "07-01-2020")
    private LocalDate returnDate;

    @NotBlank(message = "Status cannot be blank")
    @Size(max = 10, message = "Status must not exceed 10 characters")
    @Schema(description = "Status of the loan", example = "RETURNED")
    private Loan.LoanStatus status;
}
