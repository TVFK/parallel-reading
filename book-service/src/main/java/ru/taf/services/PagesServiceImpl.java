package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.entities.Page;
import ru.taf.exceptions.PageNotFoundException;
import ru.taf.repositories.PagesRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PagesServiceImpl implements PagesService {

    private final PagesRepository pagesRepository;
    @Override
    public Page findPageByNumber(int bookId, int pageNumber) {
        return pagesRepository.findPageByPageNumberAndBookId(pageNumber, bookId).orElseThrow(() ->
                new PageNotFoundException("Page with name %s not found".formatted(pageNumber)));
    }

    @Override
    public List<Page> findAllPagesByBook(int bookId) {
        return pagesRepository.findAllByBookId(bookId);
    }
}
