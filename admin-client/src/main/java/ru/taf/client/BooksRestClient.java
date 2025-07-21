package ru.taf.client;

import ru.taf.dto.BookDTO;
import ru.taf.dto.NewBookDTO;
import ru.taf.dto.UpdateBookDTO;

import java.util.List;

public interface BooksRestClient {
    List<BookDTO> findAllBooks(String filter);

    BookDTO findBook(Integer bookId);

    BookDTO createBook(NewBookDTO book);

    void updateBook(UpdateBookDTO book, Integer bookId);

    void deleteBook(Integer bookId);
}
