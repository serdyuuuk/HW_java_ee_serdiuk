package com.example.demo.controller;

import com.example.demo.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/book")
public class BookController {

    private ArrayList<Book> bookArrayList = new ArrayList<>();

    @GetMapping("/create")
    public String bookFormGet(){
        return "book-create";
    }

    @PostMapping("/create")
    public String saveBook(Book book){
        bookArrayList.add(book);
        return "redirect:/book/list";
    }

    @GetMapping("/list")
    public String bookList(Model model){
        model.addAttribute("books", bookArrayList);
        return "book-list";
    }


}
