package com.bookstore.controller;

import com.bookstore.dto.BookRequestDTO;
import com.bookstore.dto.BookResponseDTO;
import com.bookstore.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@Validated
@AllArgsConstructor
@CrossOrigin // optional: allow CORS for frontend during development
public class BookController {

    private final BookService bookService;

    // -------- Get paginated list of books --------
    @Operation(summary = "Get paginated list of books")
    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> getAllBooks(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<BookResponseDTO> page = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(page);
    }

    // -------- Get single book by id --------
    @Operation(summary = "Get book details by id")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        BookResponseDTO dto = bookService.getBookById(id);
        return ResponseEntity.ok(dto);
    }

    // -------- Search books (by title or author) --------
    @Operation(summary = "Search books by title or author (paginated)")
    @GetMapping("/search")
    public ResponseEntity<Page<BookResponseDTO>> searchBooks(
            @RequestParam(name = "q", required = false, defaultValue = "") String q,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {

        // The service.searchBooks should be implemented to search title OR author.
        Page<BookResponseDTO> page = bookService.searchBooks(q, pageable);
        return ResponseEntity.ok(page);
    }

    // -------- Add new book (Admin only) --------
    @Operation(summary = "Add new book (Admin only)")
    @PreAuthorize("hasRole('ADMIN')") // depends on how you map roles
    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO request) {
        BookResponseDTO created = bookService.addBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // -------- Update book (Admin only) --------
    @Operation(summary = "Update book (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDTO request) {

        BookResponseDTO updated = bookService.updateBook(id, request);
        return ResponseEntity.ok(updated);
    }

    // -------- Delete book (Admin only) --------
    @Operation(summary = "Delete book (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
