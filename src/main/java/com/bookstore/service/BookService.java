package com.bookstore.service;

import com.bookstore.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDTO addBook(BookRequestDTO bookRequest);
    BookResponseDTO updateBook(Long id, BookRequestDTO bookRequest);
    void deleteBook(Long id);
    BookResponseDTO getBookById(Long id);
    Page<BookResponseDTO> getAllBooks(Pageable pageable);
    Page<BookResponseDTO> searchBooks(String keyword, Pageable pageable);
}
