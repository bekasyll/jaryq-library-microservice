package com.bekasyl.message.dto;

public record MemberMsgDto(String cardNumber,
                           String memberFullName,
                           String memberIin,
                           String mobileNumber,
                           String email) {
}