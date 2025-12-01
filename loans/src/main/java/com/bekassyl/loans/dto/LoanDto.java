package com.bekassyl.loans.dto;

import com.bekassyl.loans.entity.Loan;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Loan",
        description = "Schema to hold Loan information"
)
public class LoanDto {
    @Schema(description = "Loan ID", example = "1")
    private Long id;

    @Schema(description = "Title of the book", example = "Three comrades")
    private String title;

    @Schema(description = "Author of the book", example = "Erich Maria Remarque")
    private String author;

    @Schema(description = "ISBN of the book", example = "9780141182636")
    private String isbn;

    @Schema(description = "Card number of the member", example = "708409220539")
    private String cardNumber;

    @Schema(description = "First name of the member", example = "John")
    private String firstName;

    @Schema(description = "Last name of the member", example = "Watson")
    private String lastName;

    @Schema(description = "IIN of the member", example = "180100586526")
    private String iin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Schema(description = "Loan date", example = "01-01-2020")
    private LocalDate loanDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Schema(description = "Return date", example = "07-01-2020")
    private LocalDate returnDate;

    @Schema(description = "Status of the loan", example = "RETURNED")
    private Loan.LoanStatus status;
}
