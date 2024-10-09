package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.entities.Book;
import ru.taf.exceptions.BooksNotFoundException;
import ru.taf.repositories.BooksRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;
    @Override
    public List<Book> findAllBooks(String filter) {
        return booksRepository.findAll();
    }

    @Override
    @Transactional
    public Book createBook(Book book) {
        return booksRepository.save(book);
    }

    @Override
    public Book findProduct(int bookId) {
        return booksRepository.findById(bookId).orElseThrow(() ->
                new BooksNotFoundException("Books with id %d not found".formatted(bookId))
        );
    }

    @Override
    @Transactional
    public void updateBook(int bookId, Book book) {
        book.setId(bookId);
        booksRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(int bookId) {
        booksRepository.deleteById(bookId);
    }
}
