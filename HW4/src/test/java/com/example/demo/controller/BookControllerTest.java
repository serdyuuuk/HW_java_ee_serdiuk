package com.example.demo.controller;

import com.example.demo.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void clear() throws Exception{
        mockMvc.perform(get("/book/clear"));
    }

    @Test
    void find_books() throws Exception {
        Book book = new Book("123", "123", "123");
        mockMvc.perform(post("/book/save_book")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
        ArrayList<Book> testBooks = new ArrayList<Book>();
        testBooks.add(book);
        mockMvc.perform(get("/book/find_books")
                .param("searchField", "123")).andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(testBooks)));
    }

    @Test
    void books() throws Exception {
        Book book = new Book("123", "123", "123");
        ArrayList<Book> testBooks = new ArrayList<Book>();
        testBooks.add(book);
            mockMvc.perform(post("/book/save_book")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(book)))
                    .andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(testBooks)));
    }
}