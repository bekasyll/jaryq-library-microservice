package com.bekassyl.books.controller;

import com.bekassyl.books.constants.BookConstants;
import com.bekassyl.books.dto.BookDto;
import com.bekassyl.books.dto.BooksInfoDto;
import com.bekassyl.books.dto.ErrorResponseDto;
import com.bekassyl.books.dto.ResponseDto;
import com.bekassyl.books.service.IBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(
        name = "CRUD REST APIs for Books in JaryqLibrary",
        description = "CRUD REST APIs in JaryqLibrary to CREATE, UPDATE, FETCH AND DELETE book details"
)
@RestController
@RequestMapping(path = "/api/books", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class BooksController {
    private final IBookService bookService;
    private final BooksInfoDto booksInfoDto;

    @Operation(
            summary = "Get Book Details REST API",
            description = "REST API to get Book details based on a isbn"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<BookDto> fetchBookDetails(
            @RequestParam("isbn")
            @Pattern(regexp = "\\d{13}", message = "ISBN must contain exactly 13 digits")
            String isbn
    ) {
        BookDto bookDto = bookService.fetchBook(isbn);

        return ResponseEntity.ok(bookDto);
    }

    @Operation(
            summary = "Loan Book REST API",
            description = "REST API to loan Book based on a isbn"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/loan-book")
    public boolean loanBook(
            @RequestParam("isbn")
            @Pattern(regexp = "\\d{13}", message = "ISBN must contain exactly 13 digits")
            String isbn
    ) {
        return bookService.loanBook(isbn);
    }


    @Operation(
            summary = "Return Book REST API",
            description = "REST API to return Book based on a isbn"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/return-book")
    public boolean returnBook(
            @RequestParam("isbn")
            @Pattern(regexp = "\\d{13}", message = "ISBN must contain exactly 13 digits")
            String isbn
    ) {
        return bookService.returnBook(isbn);
    }


    @Operation(
            summary = "Create Book REST API",
            description = "REST API to create a new Book in JaryqLibrary"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createBook(@RequestBody @Valid BookDto bookDto) {
        boolean isCreated = bookService.createBook(bookDto);

        if (isCreated) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(BookConstants.STATUS_201, BookConstants.MESSAGE_201));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(BookConstants.STATUS_417, BookConstants.MESSAGE_417_UPDATE));
        }
    }


    @Operation(
            summary = "Put Book Details REST API",
            description = "REST API to update Book details based on a isbn"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateBookDetails(@RequestBody @Valid BookDto bookDto) {
        boolean isUpdated = bookService.updateBook(bookDto);

        if (isUpdated) {
            return ResponseEntity
                    .ok(new ResponseDto(BookConstants.STATUS_200, BookConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(BookConstants.STATUS_417, BookConstants.MESSAGE_417_UPDATE));
        }
    }


    @Operation(
            summary = "Delete Book Details REST API",
            description = "REST API to delete Book details based on a isbn"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteBook(
            @RequestParam("isbn")
            @Pattern(regexp = "\\d{13}", message = "ISBN must contain exactly 13 digits")
            String isbn
    ) {
        boolean isDeleted = bookService.deleteBook(isbn);

        if (isDeleted) {
            return ResponseEntity
                    .ok(new ResponseDto(BookConstants.STATUS_200, BookConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(BookConstants.STATUS_417, BookConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Get Books Microservice Build Version REST API",
            description = "REST API to get Books microservice build version"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/build-version")
    public ResponseEntity<Map<String, String>> getBuildInfo() {
        Map<String, String> buildInfo = new HashMap<>();
        buildInfo.put("Build version", booksInfoDto.getBuildVersion());

        return ResponseEntity.ok(buildInfo);
    }


    @Operation(
            summary = "Get Books Microservice Info REST API",
            description = "REST API to get Books microservice info"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/info")
    public ResponseEntity<BooksInfoDto> getBooksInfo() {
        return ResponseEntity.ok(booksInfoDto);
    }
}
