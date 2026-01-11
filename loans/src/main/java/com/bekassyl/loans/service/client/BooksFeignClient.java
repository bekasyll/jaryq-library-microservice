package com.bekassyl.loans.service.client;

import com.bekassyl.loans.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "books", fallback = BooksFallback.class)
public interface BooksFeignClient {
    @GetMapping(value = "/books/api/fetch", consumes = "application/json")
    public ResponseEntity<BookDto> fetchBookDetails(@RequestParam("isbn") String isbn);

    @PostMapping(value = "/books/api/loan-book", consumes = "application/json")
    public boolean loanBook(@RequestParam("isbn") String isbn);

    @PostMapping(value = "/books/api/return-book", consumes = "application/json")
    public boolean returnBook(@RequestParam("isbn") String isbn);
}
