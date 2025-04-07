package ru.taf.services;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.taf.entities.Book;

import java.util.List;

public interface BooksService {

    List<Book> getBooks(Specification<Book> spec, Sort sort);
    List<List<Book>> getBooksGroupedByLevel();
    Book getBookById(Integer bookId);
    Book getBookByTitle(String title);
}
