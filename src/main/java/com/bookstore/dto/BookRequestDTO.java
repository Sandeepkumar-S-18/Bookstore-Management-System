package com.bookstore.dto;

import com.bookstore.entity.Book;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {

    @NotBlank(message = "Title is required")
    @Pattern(regexp = "^[A-Za-z0-9\\s'.,:;!?-]{2,100}$")
    private String title;

    @NotBlank(message = "Author is required")
    @Pattern(regexp = "^[A-Za-z\\s'.-]{2,50}$")
    private String authors;

    @NotBlank(message = "Genre is required")
    @Pattern(regexp = "^[A-Za-z\\s-]{3,30}$")
    private String genre;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$")
    private String isbn;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "Description cannot be empty")
    @Pattern(regexp = "^[A-Za-z0-9\\s.,'\"!?()-]{5,500}$")
    private String description;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stockQuantity;

    private String imageUrl;

    public static Book toEntity(BookRequestDTO bookRequestDTO) {
        Book book = new Book();
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthors(bookRequestDTO.getAuthors());
        book.setGenre(bookRequestDTO.getGenre());
        book.setIsbn(bookRequestDTO.getIsbn());
        book.setPrice(bookRequestDTO.getPrice());
        book.setDescription(bookRequestDTO.getDescription());
        book.setStockQuantity(bookRequestDTO.getStockQuantity());
        book.setImageUrl(bookRequestDTO.getImageUrl());
        return book;
    }
}
