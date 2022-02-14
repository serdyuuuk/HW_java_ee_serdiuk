package com.example.demo.controller;
import com.example.demo.model.Book;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    void setPort(int port) {
        RestAssured.port = port;
    }

    @BeforeEach
    public void clear(){
        RestAssured.when().get("/book/clear");
    }


    @Test
    public void shouldAddBook() throws Exception {
        Book book = new Book("123","123","123");
        List<Book> books = List.of(book);
        final String jsonRequest = objectMapper.writeValueAsString(book);
        List<Book> bookList = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(jsonRequest)
                .when()
                    .post("/book/save_book")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .jsonPath()
                    .getList("$", Book.class);
        assertEquals(bookList, books);
    }

    @Test
    public void shouldFindBook() throws Exception {
        Book book = new Book("123","123","123");
        Book book1 = new Book("abc","abc","abc");
        List<Book> books = List.of(book);
        String jsonRequest = objectMapper.writeValueAsString(book);
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(jsonRequest)
                .when()
                .post("/book/save_book");
        jsonRequest = objectMapper.writeValueAsString(book1);
            RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(jsonRequest)
                .when()
                .post("/book/save_book");
        List<Book> bookList = RestAssured.given()
                .param("searchField","123")
                .when().get("/book/find_books")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList("$", Book.class);
        assertEquals(bookList, books);
    }
}
