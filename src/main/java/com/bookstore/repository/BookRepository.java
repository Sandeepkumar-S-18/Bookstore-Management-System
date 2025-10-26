package com.bookstore.repository;

import com.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Book> findByAuthorsContainingIgnoreCase(String authors, Pageable pageable);
    Page<Book> findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(String title, String authors, Pageable pageable);
    Page<Book> findByGenreContainingIgnoreCase(String genre, Pageable pageable);
    Optional<Book> findByIsbn(String isbn);
}
