package com.example.demo.config;

import com.example.demo.repository.UserRepo;
import com.example.demo.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@AllArgsConstructor
public class WebConfig extends WebSecurityConfigurerAdapter {

    UserRepo repo;

    @Override
    protected UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(repo);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/books/registration", "/books/save_user").anonymous()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }
}