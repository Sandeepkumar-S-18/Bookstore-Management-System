package com.bookstore.serviceImpl;

import com.bookstore.dto.BookRequestDTO;
import com.bookstore.dto.BookResponseDTO;
import com.bookstore.entity.Book;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    // 🔹 Add a new book
    @Override
    public BookResponseDTO addBook(BookRequestDTO dto) {
        Book book = BookRequestDTO.toEntity(dto);
        Book saved = bookRepository.save(book);
        return BookResponseDTO.fromEntity(saved);
    }

    // 🔹 Update existing book
    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        Book updatedData = BookRequestDTO.toEntity(dto);

        existingBook.setTitle(updatedData.getTitle());
        existingBook.setAuthors(updatedData.getAuthors());
        existingBook.setGenre(updatedData.getGenre());
        existingBook.setIsbn(updatedData.getIsbn());
        existingBook.setPrice(updatedData.getPrice());
        existingBook.setDescription(updatedData.getDescription());
        existingBook.setStockQuantity(updatedData.getStockQuantity());
        existingBook.setImageUrl(updatedData.getImageUrl());

        Book saved = bookRepository.save(existingBook);
        return BookResponseDTO.fromEntity(saved);
    }

    // 🔹 Delete book
    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id))
            throw new ResourceNotFoundException("Book not found with id: " + id);
        bookRepository.deleteById(id);
    }

    // 🔹 Get book by ID
    @Override
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return BookResponseDTO.fromEntity(book);
    }

    // 🔹 Get all books (with pagination)
    @Override
    public Page<BookResponseDTO> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(BookResponseDTO::fromEntity);
    }

    // 🔹 Search books (title or author)
    @Override
    public Page<BookResponseDTO> searchBooks(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBooks(pageable);
        }
        return bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(keyword, keyword, pageable)
                .map(BookResponseDTO::fromEntity);
    }
}
