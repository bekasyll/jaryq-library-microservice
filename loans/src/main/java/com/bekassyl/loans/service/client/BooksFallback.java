package com.bekassyl.loans.service.client;

import com.bekassyl.loans.dto.BookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BooksFallback implements BooksFeignClient {
    @Override
    public ResponseEntity<BookDto> fetchBookDetails(String isbn) {
        return null;
    }

    @Override
    public boolean loanBook(String isbn) {
        return false;
    }

    @Override
    public boolean returnBook(String isbn) {
        return false;
    }
}
