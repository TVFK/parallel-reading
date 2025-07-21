package ru.taf.services;

import ru.taf.dto.BookDTO;
import ru.taf.dto.NewBookDTO;
import ru.taf.dto.UpdateBookDTO;
import ru.taf.entities.Book;

import java.util.List;

public interface BooksService {

    List<BookDTO> getBooks(String title, String genres, String level, String sort);
    List<List<BookDTO>> getBooksGroupedByLevel();
    Book getBookById(Integer bookId);
    BookDTO getBookByTitle(String title);
    void updateBook(Integer bookId, UpdateBookDTO bookDTO);
    void deleteBook(Integer bookId);

    BookDTO createBook(NewBookDTO newBook);
}
