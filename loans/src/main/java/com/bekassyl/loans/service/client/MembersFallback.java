package com.bekassyl.loans.service.client;

import com.bekassyl.loans.dto.MemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MembersFallback implements MembersFeignClient {
    @Override
    public ResponseEntity<MemberDto> fetchMemberByCardNumber(String cardNumber) {
        return null;
    }

    @Override
    public ResponseEntity<MemberDto> fetchMemberByIin(String iin) {
        return null;
    }
}
