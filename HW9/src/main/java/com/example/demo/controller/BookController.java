package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    UserRepo userRepo;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showHtml(Model model, @RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "size",defaultValue = "10") int size){
        model.addAttribute("pageNumber", 1);
        model.addAttribute("books", bookService.findBooksPaginated(page, size));
        return "book-main";
    }


    @RequestMapping(value = "/wishList", method = RequestMethod.GET)
    public String showHtml(Model model, Authentication authentication){
        User user = userRepo.findByLogin(authentication.getName()).get();
        List<Book> wishList = user.getBooks();
        model.addAttribute("books", wishList);
        return "book-wish";
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(){
        return "book-registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String save_user(@RequestParam(name = "login") String login,
                          @RequestParam(name = "password") String password) {
        userRepo.save(User.builder().login(login).password(password).build());
        return "redirect:/books/main";
    }

    @RequestMapping(value = "/find_all_books_number", method = RequestMethod.GET)
    @ResponseBody
    public long find_all_books(){
        return bookService.findAllBooks().size();
    }

    @RequestMapping(value = "/find_books", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> find_books(@RequestParam("searchField") String search){
        if (search.isEmpty()){
            return bookService.findBooksPaginated(1, 10);
        }
        return bookService.findBooks(search);
    }

    @RequestMapping(value = "/save_book", method = RequestMethod.POST)
    @ResponseBody
    public List<Book> books(@RequestBody @Valid final Book book) {
        bookService.createBook(book);
        return  bookService.findBooksPaginated(1, 10);
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public String showHtmlBook(Model model, @PathVariable(name="id") long id){
        Book book = bookService.findBookById(id);
        if (book != null) {
            model.addAttribute("book", book);
            return "book";
        }
        return "wrong_id_book";
    }

    @RequestMapping(value = "/add_to_wishlist/{id}", method = RequestMethod.GET)
    public String addDeleteWishList(Model model, @PathVariable(name="id") long id, Authentication authentication){
        User user = userRepo.findByLogin(authentication.getName()).get();
        List<Book> wishList = user.getBooks();
        if (wishList.stream().anyMatch(book -> book.getId()==id)) {
            wishList = wishList.stream().dropWhile(book -> book.getId()==id).collect(Collectors.toList());
        } else {
            wishList.add(bookService.findBookById(id));
        }
        user.setBooks(wishList);
        userRepo.save(user);
        return "redirect:/books/main";
    }

}
