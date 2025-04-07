package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.entities.Book;
import ru.taf.entities.Page;
import ru.taf.repositories.BooksRepository;
import ru.taf.repositories.PagesRepository;
import ru.taf.exceptions.BookNotFoundException;
import ru.taf.exceptions.PageNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PagesServiceImpl implements PagesService {

    private final PagesRepository pagesRepository;

    @Override
    public Page findPageByNumber(Integer bookId, int pageNumber) {
        return pagesRepository.findPageByPageNumberAndBookId(bookId, pageNumber).orElseThrow(() ->
                new PageNotFoundException("page.not_found", pageNumber));
    }

    @Override
    public List<Page> findAllPagesByBook(int bookId) {
        return pagesRepository.findAllPagesByBookId(bookId);
    }

    // FIXME сделать нормальную реализацию (исправь эту хуйню)
    @Override
    public Page getNextPage(Integer pageId) {
        return pagesRepository.findById(pageId+1).orElseThrow(() ->
                new PageNotFoundException("page.not_found", pageId));
    }

    @Override
    public Page getPrevPage(Integer pageId) {
        return pagesRepository.findById(pageId-1).orElseThrow(() ->
                new PageNotFoundException("page.not_found", pageId));
    }
}
