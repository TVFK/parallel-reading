package ru.taf.client;

import ru.taf.dto.Book;
import ru.taf.dto.NewBookDTO;
import ru.taf.dto.UpdateBookDTO;

import java.util.List;

public interface BooksRestClient {
    List<Book> findAllBooks(String filter);

    Book findBook(Integer bookId);

    Book createBook(NewBookDTO book);

    void updateBook(UpdateBookDTO book, Integer bookId);

    void deleteBook(Integer bookId);
}
