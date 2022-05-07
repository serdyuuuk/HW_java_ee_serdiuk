package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Login can't be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+", message = "Login has bad format")
    private String login;

    @NotEmpty(message = "Password can't be empty")
    @Pattern(regexp = "^.{8,20}$", message = "Password has bad format")
    private String password;

    @ManyToMany
    @JoinTable(name = "user_book",
            joinColumns = @JoinColumn(name = "userID"),
            inverseJoinColumns = @JoinColumn(name = "bookID"))
    List<Book> books;
}