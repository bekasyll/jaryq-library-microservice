package com.bekassyl.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Member",
        description = "Schema to hold Member information"
)
public class MemberDto {
    @Schema(description = "Card number of the member", example = "708409220539")
    @Size(max = 12, message = "Card number must not exceed 12 characters")
    private String cardNumber;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 255, message = "First name must not exceed 255 characters")
    @Schema(description = "First name of the member", example = "John")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 255, message = "Last name must not exceed 255 characters")
    @Schema(description = "Last name of the member", example = "Watson")
    private String lastName;

    @NotBlank(message = "IIN cannot be blank")
    @Size(max = 12, message = "IIN must not exceed 12 characters")
    @Schema(description = "IIN of the member", example = "180100586526")
    private String iin;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email address should be a valid value")
    @Size(max = 254, message = "Email must not exceed 254 characters")
    @Schema(description = "Email address of the member", example = "john@gmail.com")
    private String email;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "^\\+\\d{11,15}$", message = "Mobile number must be 11-15 digits and start with '+'")
    @Schema(description = "Mobile number of the member", example = "+77711223344")
    private String mobileNumber;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    @Schema(description = "Address of the member", example = "12 Kabanbai batyr, Astana city")
    private String address;
}