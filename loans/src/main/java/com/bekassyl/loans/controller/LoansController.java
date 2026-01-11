package com.bekassyl.loans.controller;

import com.bekassyl.loans.dto.*;
import com.bekassyl.loans.dto.request.LoanRequestDto;
import com.bekassyl.loans.dto.response.ErrorResponseDto;
import com.bekassyl.loans.dto.response.LoanDetailsResponseDto;
import com.bekassyl.loans.dto.response.LoanInfoResponseDto;
import com.bekassyl.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(
        name = "CRUD REST APIs for Loans in JaryqLibrary",
        description = "CRUD REST APIs in JaryqLibrary to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(value = "/loans/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
@Slf4j
public class LoansController {
    private final ILoanService loanService;
    private final LoanInfoResponseDto loanInfoResponseDto;

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
    public ResponseEntity<List<LoanDto>> fetchLoanDetailsByBookIsbn(
            @RequestParam("bookIsbn")
            @Pattern(regexp = "\\d{13}", message = "ISBN must contain exactly 13 digits") String bookIsbn)
    {
        log.debug("fetchLoanDetailsByBookIsbn() method starts");

        List<LoanDto> loanDtoList = loanService.fetchLoansByBookIsbn(bookIsbn);

        log.debug("fetchLoanDetailsByBookIsbn() method ends");

        return ResponseEntity.ok(loanDtoList);
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
    public ResponseEntity<List<LoanDto>> fetchLoanDetailsByMemberIin(
            @RequestParam("memberIin")
            @Size(max = 12, message = "IIN must not exceed 12 characters") String memberIin)
    {
        log.debug("fetchLoanDetailsByMemberIin() method starts");

        List<LoanDto> loanDtoList = loanService.fetchLoansByMemberIin(memberIin);

        log.debug("fetchLoanDetailsByMemberIin() method ends");

        return ResponseEntity.ok(loanDtoList);
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
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<LoanDetailsResponseDto> createLoan(
            @RequestBody @Valid LoanRequestDto requestDto
    ) {
        log.debug("createLoan() method starts");

        LoanDetailsResponseDto responseDto = loanService.createLoan(requestDto);

        log.debug("createLoan() method ends");

        return ResponseEntity.ok(responseDto);
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
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/return-book")
    public ResponseEntity<LoanDetailsResponseDto> returnBook(
            @RequestBody @Valid LoanRequestDto requestDto
    ) {
        log.debug("returnBook() method starts");

        LoanDetailsResponseDto responseDto = loanService.returnBook(requestDto);

        log.debug("returnBook() method ends");

        return ResponseEntity.ok(responseDto);
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
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/extend-loan")
    public ResponseEntity<LoanDetailsResponseDto> extendLoan(
            @RequestBody @Valid LoanRequestDto requestDto
    ) {
        log.debug("extendLoan() method starts");

        LoanDetailsResponseDto responseDto = loanService.extendLoan(requestDto);

        log.debug("extendLoan() method ends");

        return ResponseEntity.ok(responseDto);
    }


    @Operation(
            summary = "Get Loans Microservice Build Version REST API",
            description = "REST API to get Loans microservice build version"
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
        buildInfo.put("Build version", loanInfoResponseDto.getBuildVersion());

        return ResponseEntity.ok(buildInfo);
    }

    @Operation(
            summary = "Get Loans Microservice Info REST API",
            description = "REST API to get Loans microservice info"
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
    public ResponseEntity<LoanInfoResponseDto> getLoansInfo() {
        return ResponseEntity.ok(loanInfoResponseDto);
    }
}
