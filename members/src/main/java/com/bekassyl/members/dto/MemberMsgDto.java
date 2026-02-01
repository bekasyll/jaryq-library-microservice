package com.bekassyl.members.dto;

public record MemberMsgDto(String cardNumber,
                           String memberFullName,
                           String memberIin,
                           String mobileNumber,
                           String email) {
}