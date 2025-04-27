package ru.taf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BooksServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BooksServiceApplication.class, args);
    }
}