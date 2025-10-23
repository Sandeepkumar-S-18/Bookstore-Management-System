package com.bookstore.dto;

import com.bookstore.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {
    private Long id;
    private String title;
    private String authors;
    private String genre;
    private String isbn;
    private BigDecimal price;
    private String description;
    private int stockQuantity;
    private String imageUrl;

    public static BookResponseDTO fromEntity(Book book) {
        return new BookResponseDTO(book.getId(), book.getTitle(), book.getAuthors(), book.getGenre(), book.getIsbn(), book.getPrice(), book.getDescription(), book.getStockQuantity(), book.getImageUrl());
    }
}
