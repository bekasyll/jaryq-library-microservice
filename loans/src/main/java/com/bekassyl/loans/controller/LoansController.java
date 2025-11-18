package com.bekassyl.loans.controller;

import com.bekassyl.loans.constants.LoanConstants;
import com.bekassyl.loans.dto.*;
import com.bekassyl.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
        name = "CRUD REST APIs for Loans in JaryqLibrary",
        description = "CRUD REST APIs in JaryqLibrary to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(value = "/api/loans", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class LoansController {
    private final ILoanService loanService;

    @Operation(
            summary = "Get Loan Details By Book Id REST API",
            description = "REST API to get Loan details based on a book id"
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
    @GetMapping("/fetch-by-book")
    public ResponseEntity<ResponseDto> fetchLoanDetailsByBookId(@RequestParam("bookId") @Positive Long bookId) {
        LoanDto loanDto = loanService.fetchLoanByBookId(bookId);

        return ResponseEntity.ok(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200, loanDto, null));
    }

    @Operation(
            summary = "Get Loan Details By Member Id REST API",
            description = "REST API to get Loan details based on a member id"
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
    @GetMapping("/fetch-by-member")
    public ResponseEntity<ResponseDto> fetchLoanDetailsByMemberId(@RequestParam("memberId") @Positive Long memberId) {
        List<LoanDto> loanDtoList = loanService.fetchLoanByMemberId(memberId);

        return ResponseEntity.ok(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200, null, loanDtoList));
    }

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create a new Loan in JaryqLibrary"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"),
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
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestBody @Valid LoanRequestDto requestDto) {
        boolean isCreated = loanService.createLoan(requestDto);

        if (isCreated) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(LoanConstants.STATUS_201, LoanConstants.MESSAGE_201, null, null));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_CREATE, null, null));
        }
    }

    @Operation(
            summary = "Return Book REST API",
            description = "REST API to return a book in JaryqLibrary"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"),
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
    @PostMapping("/return-book")
    public ResponseEntity<ResponseDto> returnBook(@RequestBody @Valid LoanIdRequestDto loanIdRequestDto) {
        boolean isReturned = loanService.returnBook(loanIdRequestDto.getId());

        if (isReturned) {
            return ResponseEntity.ok(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200, null, null));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_RETURN, null, null));
        }
    }

    @Operation(
            summary = "Extend Loan REST API",
            description = "REST API to extend a loan in JaryqLibrary"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"),
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
    @PostMapping("/extend-loan")
    public ResponseEntity<ResponseDto> extendLoan(@RequestBody @Valid LoanIdRequestDto loanIdRequestDto) {
        boolean isExtended = loanService.extendLoan(loanIdRequestDto.getId());

        if (isExtended) {
            return ResponseEntity.ok(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200, null, null));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_EXTEND, null, null));
        }
    }

    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a loan id"
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
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam("id") @Positive Long id) {
        boolean isDeleted = loanService.deleteLoan(id);

        if (isDeleted) {
            return ResponseEntity.ok(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200, null, null));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_EXTEND, null, null));
        }
    }

}
