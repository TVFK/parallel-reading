package ru.taf.services;

import ru.taf.entities.Book;

import java.util.List;

public interface BooksService {

    List<Book> findAllBooks(String filter);

    Book createBook(Book book);

    Book findProduct(int bookId);

    void updateBook(int bookId, Book book);

    void deleteBook(int bookId);
}
