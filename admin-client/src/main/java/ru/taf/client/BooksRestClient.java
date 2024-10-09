package ru.taf.client;

import ru.taf.entities.Book;

import java.util.List;

public interface BooksRestClient {
    List<Book> findAllBooks(String filter);

    Book findBook(Integer bookId);

    Book createBook(Book book);

    void updateBook(Book book, Integer bookId);

    void deleteBook(Integer bookId);
}
