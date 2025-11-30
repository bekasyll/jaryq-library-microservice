package com.bekassyl.loans.service.client;

import com.bekassyl.loans.dto.MemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("members")
public interface MembersFeignClient {
    @GetMapping(value = "/api/members/fetch-by-card", consumes = "application/json")
    public ResponseEntity<MemberDto> fetchMemberByCardNumber(@RequestParam("cardNumber") String cardNumber);

    @GetMapping(value = "/api/members/fetch-by-iin", consumes = "application/json")
    public ResponseEntity<MemberDto> fetchMemberByIin(@RequestParam("iin") String iin);
}
