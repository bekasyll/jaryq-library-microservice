package com.bekassyl.books.service;

import com.bekassyl.books.dto.BookDto;
import com.bekassyl.books.exception.ResourceNotFoundException;
import com.bekassyl.books.exception.BookAlreadyExistsException;

public interface IBookService {
    /**
     * Finds book details by isbn.
     *
     * @param isbn isbn to search for
     * @return DTO containing book details for the given isbn
     * @throws ResourceNotFoundException if a book is not found
     */
    BookDto fetchBook(String isbn);

    /**
     * Loan book by isbn.
     *
     * @param isbn isbn to search for
     * @return {@code true} if loan was successful, {@code false} otherwise
     */
    boolean loanBook(String isbn);

    /**
     * Return book by isbn.
     *
     * @param isbn isbn to search for
     * @return {@code true} if return was successful, {@code false} otherwise
     */
    boolean returnBook(String isbn);

    /**
     * Saves a new book.
     *
     * @param bookDto book data transfer object
     * @throws BookAlreadyExistsException if a book with the given isbn already exists
     */
    boolean createBook(BookDto bookDto);

    /**
     * Updates book details, excluding the isbn.
     *
     * @param bookDto DTO containing updated book information
     * @return {@code true} if update was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a book is not found
     */
    boolean updateBook(BookDto bookDto);

    /**
     * Deletes a book.
     *
     * @param isbn isbn to identify the book
     * @return {@code true} if delete was successful, {@code false} otherwise
     * @throws ResourceNotFoundException if a book is not found
     */
    boolean deleteBook(String isbn);
}
