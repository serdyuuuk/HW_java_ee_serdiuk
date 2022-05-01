package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo  extends JpaRepository<Book, Long> {
    List<Book> findBooksByAuthorContainsOrTitleContainsOrIsbnContains(String val1, String val2, String val3);
}
