package com.bekassyl.loans.dto;

import java.time.LocalDate;

public record LoanMsgDto(String cardNumber,
                         String memberFullName,
                         String memberIin,
                         String mobileNumber,
                         String email,
                         String bookName,
                         String bookIsbn,
                         LocalDate loanDate) {
}