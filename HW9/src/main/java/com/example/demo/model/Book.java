package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Entity
@Table(name = "books")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Book {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;


    @NotEmpty(message = "ISBN can't be empty")
    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$", message = "ISBN has bad format")
    @Column(name = "isbn")
    private String  isbn;

    @NotEmpty(message = "Book title can't be empty")
    @Column(name = "book_title")
    private String title;

    @NotEmpty(message = "Author name can't be empty")
    @Column(name = "author_name")
    private String author;


    @ManyToMany(mappedBy = "books")
    List<User> users;

}
