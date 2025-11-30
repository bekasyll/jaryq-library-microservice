package com.bekassyl.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Book",
        description = "Schema to hold Book information"
)
public class BookDto {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    @Schema(description = "Title of the book", example = "Three comrades")
    private String title;

    @NotBlank(message = "Author cannot be blank")
    @Size(max = 255, message = "Author name must not exceed 255 characters")
    @Schema(description = "Author of the book", example = "Erich Maria Remarque")
    private String author;

    @NotBlank(message = "Genre cannot be blank")
    @Size(max = 30, message = "Genre must not exceed 30 characters")
    @Schema(description = "Genre of the book", example = "Classic")
    private String genre;

    @NotBlank(message = "ISBN cannot be blank")
    @Pattern(regexp = "\\d{13}", message = "ISBN must contain exactly 13 digits")
    @Schema(description = "ISBN of the book", example = "9780141182636")
    private String isbn;

    @NotNull(message = "Total copies cannot be null")
    @Positive(message = "Total copies must be a positive number")
    @Schema(description = "Total copies of the book in library", example = "100")
    private int totalCopies;

    @NotNull(message = "Available copies cannot be null")
    @PositiveOrZero(message = "Available copies cannot be negative")
    @Schema(description = "Available copies of the book in library", example = "40")
    private int availableCopies;
}
