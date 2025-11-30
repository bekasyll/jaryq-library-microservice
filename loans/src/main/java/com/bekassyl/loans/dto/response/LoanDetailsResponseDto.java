package com.bekassyl.loans.dto.response;

import com.bekassyl.loans.dto.BookDto;
import com.bekassyl.loans.dto.MemberDto;
import com.bekassyl.loans.entity.Loan;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Schema(
        name = "Loan Details",
        description = "Schema to hold Loan Details information"
)
public class LoanDetailsResponseDto {
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

    @Schema(
            description = "Member details"
    )
    private MemberDto memberDto;

    @Schema(
            description = "Book details"
    )
    private BookDto bookDto;
}
