package com.bekassyl.books.service.impl;

import com.bekassyl.books.dto.BookDto;
import com.bekassyl.books.entity.Book;
import com.bekassyl.books.mapper.BookMapper;
import com.bekassyl.books.repository.BookRepository;
import com.bekassyl.books.service.IBookService;
import lombok.RequiredArgsConstructor;
import com.bekassyl.books.exception.ResourceNotFoundException;
import com.bekassyl.books.exception.BookAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    /**
     * Finds book details by isbn.
     *
     * @param isbn isbn to search for
     * @return DTO containing book details for the given isbn
     * @throws ResourceNotFoundException if a book is not found
     */
    @Override
    public BookDto fetchBook(String isbn) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(
                () -> new ResourceNotFoundException("Book", "ISBN", isbn)
        );

        return bookMapper.toDto(book);
    }

    /**
     * Saves a new book.
     *
     * @param bookDto book data transfer object
     * @throws BookAlreadyExistsException if a book with the given isbn already exists
     */
    @Transactional
    @Override
    public boolean createBook(BookDto bookDto) {
        if (bookRepository.findByIsbn(bookDto.getIsbn()).isPresent()) {
            throw new BookAlreadyExistsException("Book with ISBN " + bookDto.getIsbn() + " already exists");
        }

        Book book = bookMapper.toEntity(bookDto);

        bookRepository.save(book);

        return true;
    }

    /**
     * Updates book details, excluding the isbn.
     *
     * @param bookDto DTO containing updated book information
     * @return {@code true} if update was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a book is not found
     */
    @Transactional
    @Override
    public boolean updateBook(BookDto bookDto) {
        Book book = bookRepository.findByIsbn(bookDto.getIsbn()).orElseThrow(
                () -> new ResourceNotFoundException("Book", "ISBN", bookDto.getIsbn())
        );

        Book updatedBook = bookMapper.toEntity(bookDto);
        updatedBook.setId(book.getId());

        bookRepository.save(updatedBook);

        return true;
    }

    /**
     * Deletes a book.
     *
     * @param isbn isbn to identify the book
     * @return {@code true} if delete was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a book is not found
     */
    @Transactional
    @Override
    public boolean deleteBook(String isbn) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(
                () -> new ResourceNotFoundException("Book", "ISBN", isbn)
        );
        bookRepository.delete(book);

        return true;
    }
}
