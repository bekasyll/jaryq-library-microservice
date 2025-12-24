package com.bekassyl.loans.service.client;

import com.bekassyl.loans.dto.BookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BooksFallback implements BooksFeignClient {
    @Override
    public ResponseEntity<BookDto> fetchBookDetails(String isbn, String correlationId) {
        return null;
    }

    @Override
    public boolean loanBook(String isbn, String correlationId) {
        return false;
    }

    @Override
    public boolean returnBook(String isbn, String correlationId) {
        return false;
    }
}
