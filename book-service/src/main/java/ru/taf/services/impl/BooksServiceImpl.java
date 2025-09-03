package ru.taf.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.dto.BookDTO;
import ru.taf.dto.NewBookDTO;
import ru.taf.dto.UpdateBookDTO;
import ru.taf.dto.mappers.BookMapper;
import ru.taf.entities.Book;
import ru.taf.exceptions.BookNotFoundException;
import ru.taf.repositories.BooksRepository;
import ru.taf.services.BooksService;
import ru.taf.specifications.BookSpecifications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDTO> getBooks(String title, String genres, String level, String sort) {

        Specification<Book> spec = BookSpecifications.withFilters(title, genres, level);
        Sort sortOrder = Sort.by(Sort.Order.asc("publishedYear"));

        if (sort != null) {
            String[] sortParams = sort.split(",");
            if (sortParams.length == 2) {
                sortOrder = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
            }
        }
        return booksRepository.findAll(spec, sortOrder)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    // TODO сделать через view/func в PostgreSQL
    @Override
    @Cacheable(value = "BookService::getBooksGroupedByLevels")
    public List<List<BookDTO>> getBooksGroupedByLevel() {
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

        return booksByLevel.stream()
                .map(booksGroup -> booksGroup
                        .stream()
                        .map(bookMapper::toDto)
                        .toList())
                .toList();
    }

    @Override
    public Book getBookById(Integer bookId) {

        return booksRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("book.not_found", bookId));
    }

    @Override
    public BookDTO getBookByTitle(String title) {
        Book book = booksRepository.findByTitle(title).orElseThrow(() ->
                new BookNotFoundException("book.not_found", title));

        return bookMapper.toDto(book);
    }

    @Override
    @Transactional
    public void updateBook(Integer bookId, UpdateBookDTO bookDTO) {
        booksRepository.findById(bookId)
                .ifPresentOrElse(book -> {
                    book.setAuthor(bookDTO.author());
                    book.setTitle(bookDTO.title());
                    book.setPublishedYear(bookDTO.publishedYear());
                    book.setDescription(bookDTO.description());
                }, () -> {
                    throw new BookNotFoundException("book.not_found", bookId);
                });
    }

    @Override
    @Transactional
    public void deleteBook(Integer bookId) {
        booksRepository.deleteById(bookId);
    }

    @Override
    @Transactional
    public BookDTO createBook(NewBookDTO newBook) {
        Book entity = bookMapper.toEntity(newBook);

        Book createdBook = booksRepository.save(entity);
        return bookMapper.toDto(createdBook);
    }
}
