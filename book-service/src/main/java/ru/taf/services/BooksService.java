package ru.taf.services;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.taf.dto.BookDTO;
import ru.taf.entities.Book;

import java.util.List;

public interface BooksService {

    List<BookDTO> getBooks(String title, String genres, String level, String sort);
    List<List<BookDTO>> getBooksGroupedByLevel();
    Book getBookById(Integer bookId);
    BookDTO getBookByTitle(String title);
}
