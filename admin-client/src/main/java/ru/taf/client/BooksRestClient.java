package ru.taf.client;

import ru.taf.dto.BookDTO;
import ru.taf.dto.NewBookDTO;

import java.util.List;

public interface BooksRestClient {
    List<BookDTO> findAllBooks(String filter);

    BookDTO findBook(Integer bookId);

    BookDTO createBook(NewBookDTO book);

    void updateBook(BookDTO book, Integer bookId);

    void deleteBook(Integer bookId);
}
