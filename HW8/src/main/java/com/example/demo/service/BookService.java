package com.example.demo.service;


import com.example.demo.model.Book;
import com.example.demo.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;

    @Transactional
    public Book createBook(Book book) {
        return bookRepo.save(book);
    }

    @Transactional
    public List<Book> findBooks(String search){
        return bookRepo.findBooksByAuthorContainsOrTitleContainsOrIsbnContains(search,search,search);
    }


    @Transactional
    public List<Book> findAllBooks(){
        List<Book> select_u_from_book_u = bookRepo.findAll();
        return select_u_from_book_u;
    }

    @Transactional
    public List<Book> findBooksPaginated(int page, int size){
        Pageable bookPage = PageRequest.of(page - 1, size);
        return bookRepo.findAll(bookPage).getContent();
    }

    @Transactional
    public Book findBookById(long id){
        return bookRepo.findById(id).get();
    }
}
