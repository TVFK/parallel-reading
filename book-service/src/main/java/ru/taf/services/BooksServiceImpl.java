package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.entities.Book;
import ru.taf.exceptions.BookNotFoundException;
import ru.taf.repositories.BooksRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;

    @Override
    public List<Book> getBooks(Specification<Book> spec, Sort sort) {
        return booksRepository.findAll(spec, sort);
    }

    // TODO сделать через view/func в PostgreSQL
    @Override
    public List<List<Book>> getBooksGroupedByLevel() {
        List<Book> books = booksRepository.findAll();
        List<List<Book>> booksByLevel = new ArrayList<>();
        List<String> levelsOrder = Arrays.asList("A1", "A2", "B1", "B2", "C1");

        for (int i = 0; i < 5; i++) {
            booksByLevel.add(new ArrayList<>());
        }

        for (Book book : books) {
            int levelIndex = levelsOrder.indexOf(book.getLevel());
            if (levelIndex != -1) {
                booksByLevel.get(levelIndex).add(book);
            }
        }

        return booksByLevel;
    }

    @Override
    public Book getBookById(Integer bookId) {
        return booksRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("book.not_found", bookId));
    }

    @Override
    public Book getBookByTitle(String title) {
        return booksRepository.findByTitle(title).orElseThrow(() ->
                new BookNotFoundException("book.not_found", title));
    }
}
