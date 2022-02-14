package com.example.demo.controller;

import com.example.demo.model.Book;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/book")
public class BookController {

    private ArrayList<Book> bookArrayList = new ArrayList<>();

    @GetMapping("/clear")
    @ResponseBody
    public Object clear(){
        bookArrayList.clear();
        return null;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showHtml(Model model){
        model.addAttribute("books", bookArrayList);
        return "book-main";
    }

    @RequestMapping(value = "/find_books", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Book> find_books(@RequestParam("searchField") String search){
        if (search.isEmpty()){
            return bookArrayList;
        }
        ArrayList<Book> findList = new ArrayList<Book>();
        for (Book b:
             bookArrayList) {
            if (b.getAuthor().contains(search) || b.getIsbn().contains(search) || b.getTitle().contains(search)){
                findList.add(b);
            }
        }
        return findList;
    }

    @RequestMapping(value = "/save_book", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<Book> books(@RequestBody final Book book) {
        bookArrayList.add(book);
        return bookArrayList;
    }


}
