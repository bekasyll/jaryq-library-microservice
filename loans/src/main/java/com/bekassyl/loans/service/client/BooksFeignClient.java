package com.bekassyl.loans.service.client;

import com.bekassyl.loans.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("books")
public interface BooksFeignClient {
    @GetMapping(value = "/api/books/fetch", consumes = "application/json")
    public ResponseEntity<BookDto> fetchBookDetails(@RequestParam("isbn") String isbn);

    @PostMapping(value = "/api/books/loan-book", consumes = "application/json")
    public boolean loanBook(@RequestParam("isbn") String isbn);

    @PostMapping(value = "/api/books/return-book", consumes = "application/json")
    public boolean returnBook(@RequestParam("isbn") String isbn);
}
