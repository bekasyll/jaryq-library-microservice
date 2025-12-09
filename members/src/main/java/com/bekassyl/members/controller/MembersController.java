package com.bekassyl.members.controller;

import com.bekassyl.members.constants.MemberConstants;
import com.bekassyl.members.dto.ErrorResponseDto;
import com.bekassyl.members.dto.MemberDto;
import com.bekassyl.members.dto.MembersInfoDto;
import com.bekassyl.members.dto.ResponseDto;
import com.bekassyl.members.service.IMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(
        name = "CRUD REST APIs for Members in JaryqLibrary",
        description = "CRUD REST APIs in JaryqLibrary to CREATE, UPDATE, FETCH AND DELETE member details"
)
@RestController
@RequestMapping(path = "/members/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class MembersController {
    private final IMemberService memberService;
    private final MembersInfoDto membersInfoDto;

    @Operation(
            summary = "Get Member Details REST API",
            description = "REST API to get Member details based on a iin"
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
    @GetMapping("/fetch-by-card")
    public ResponseEntity<MemberDto> fetchMemberByCardNumber(
            @RequestParam("cardNumber")
            @Size(max = 12, message = "Card number must not exceed 12 characters")
            String cardNumber
    ) {
        MemberDto memberDto = memberService.fetchMemberByCardNumber(cardNumber);

        return ResponseEntity.ok(memberDto);
    }


    @Operation(
            summary = "Get Member Details REST API",
            description = "REST API to get Member details based on a iin"
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
    @GetMapping("/fetch-by-iin")
    public ResponseEntity<MemberDto> fetchMemberByIin(
            @RequestParam("iin")
            @Size(max = 12, message = "IIN must not exceed 12 characters")
            String iin
    ) {
        MemberDto memberDto = memberService.fetchMemberByIin(iin);

        return ResponseEntity.ok(memberDto);
    }


    @Operation(
            summary = "Create Member REST API",
            description = "REST API to create a new Member in JaryqLibrary"
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
    public ResponseEntity<ResponseDto> createMember(@RequestBody @Valid MemberDto memberDto) {
        boolean isCreated = memberService.createMember(memberDto);

        if (isCreated) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(MemberConstants.STATUS_201, MemberConstants.MESSAGE_201));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(MemberConstants.STATUS_417, MemberConstants.MESSAGE_417_UPDATE));
        }

    }


    @Operation(
            summary = "Put Member Details REST API",
            description = "REST API to update Member details based on a iin"
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
    public ResponseEntity<ResponseDto> updateMemberDetails(@RequestBody @Valid MemberDto memberDto) {
        boolean isUpdated = memberService.updateMember(memberDto);

        if (isUpdated) {
            return ResponseEntity
                    .ok(new ResponseDto(MemberConstants.STATUS_200, MemberConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(MemberConstants.STATUS_417, MemberConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Member Details REST API",
            description = "REST API to delete Member details based on a card number"
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
    @DeleteMapping("/delete-by-card")
    public ResponseEntity<ResponseDto> deleteMemberById(
            @RequestParam("cardNumber")
            @Size(max = 12, message = "Card number must not exceed 12 characters")
            String cardNumber
    ) {
        boolean isDeleted = memberService.deleteMemberByCardNumber(cardNumber);

        if (isDeleted) {
            return ResponseEntity
                    .ok(new ResponseDto(MemberConstants.STATUS_200, MemberConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(MemberConstants.STATUS_417, MemberConstants.MESSAGE_417_DELETE));
        }
    }


    @Operation(
            summary = "Delete Member Details REST API",
            description = "REST API to delete Member details based on a iin"
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
    @DeleteMapping("/delete-by-iin")
    public ResponseEntity<ResponseDto> deleteMemberByIIN(
            @RequestParam("iin")
            @Size(max = 12, message = "IIN must not exceed 12 characters")
            String iin
    ) {
        boolean isDeleted = memberService.deleteMemberByIin(iin);

        if (isDeleted) {
            return ResponseEntity
                    .ok(new ResponseDto(MemberConstants.STATUS_200, MemberConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(MemberConstants.STATUS_417, MemberConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Get Members Microservice Build Version REST API",
            description = "REST API to get Members microservice build version"
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
        buildInfo.put("Build version", membersInfoDto.getBuildVersion());

        return ResponseEntity.ok(buildInfo);
    }

    @Operation(
            summary = "Get Members Microservice Info REST API",
            description = "REST API to get Members microservice info"
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
    public ResponseEntity<MembersInfoDto> getLoansInfo() {
        return ResponseEntity.ok(membersInfoDto);
    }
}