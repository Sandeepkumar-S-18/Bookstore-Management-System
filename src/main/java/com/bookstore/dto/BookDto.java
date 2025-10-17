package com.bookstore.dto;

import com.bookstore.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private BigDecimal price;
    private String description;
    private int stockQuantity;
    private String imageUrl;

    public static BookDto fromEntity(Book book) {
        return new BookDto(book.getTitle(), book.getAuthor(), book.getGenre(), book.getIsbn(), book.getPrice(), book.getDescription(), book.getStockQuantity(), book.getImageUrl());
    }

    public static Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());
        book.setIsbn(bookDto.getIsbn());
        book.setPrice(bookDto.getPrice());
        book.setDescription(bookDto.getDescription());
        book.setStockQuantity(bookDto.getStockQuantity());
        book.setImageUrl(bookDto.getImageUrl());
        return book;
    }
}
