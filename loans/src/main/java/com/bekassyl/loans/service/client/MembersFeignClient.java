package com.bekassyl.loans.service.client;

import com.bekassyl.loans.dto.MemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "members", fallback = MembersFallback.class)
public interface MembersFeignClient {
    @GetMapping(value = "/members/api/fetch-by-card", consumes = "application/json")
    public ResponseEntity<MemberDto> fetchMemberByCardNumber(@RequestParam("cardNumber") String cardNumber);

    @GetMapping(value = "/members/api/fetch-by-iin", consumes = "application/json")
    public ResponseEntity<MemberDto> fetchMemberByIin(@RequestParam("iin") String iin);
}