package ru.taf.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.dto.PageDTO;
import ru.taf.entities.Page;
import ru.taf.dto.mappers.PageMapper;
import ru.taf.repositories.PagesRepository;
import ru.taf.exceptions.PageNotFoundException;
import ru.taf.services.PagesService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PagesServiceImpl implements PagesService {

    private final PagesRepository pagesRepository;
    private final PageMapper pageMapper;

    @Override
    public PageDTO findPageByNumber(Integer bookId, int pageNumber) {
        Page page = pagesRepository.findPageByPageNumberAndBookId(bookId, pageNumber).orElseThrow(() -> {
            log.error("Page not found. BookId={}, PageNumer={}", bookId, pageNumber);
            return new PageNotFoundException("page.not_found", pageNumber);
        });


        return pageMapper.toDto(page);
    }

    @Override
    public List<PageDTO> findAllPagesByBook(int bookId) {
        return pagesRepository.findAllPagesByBookId(bookId)
                .stream()
                .map(pageMapper::toDto)
                .collect(Collectors.toList());
    }

    // FIXME сделать нормальную реализацию
    @Override
    public PageDTO getNextPage(Integer pageId) {
        Page page = pagesRepository.findById(pageId + 1).orElseThrow(() -> {
            log.error("Page not found. PageId={}", pageId);
            return new PageNotFoundException("page.not_found", pageId);
        });

        return pageMapper.toDto(page);
    }

    @Override
    public PageDTO getPrevPage(Integer pageId) {
        Page page = pagesRepository.findById(pageId + 1).orElseThrow(() -> {
            log.error("Page not found. PageId={}", pageId);
            return new PageNotFoundException("page.not_found", pageId);
        });

        return pageMapper.toDto(page);
    }

    @Override
    @Cacheable(value = "ChapterService::getFirstPage", key = "#chapterId")
    public PageDTO getFirstPage(Integer chapterId) {
        Page page = pagesRepository.findFirstByChapter_Id(chapterId).orElseThrow(() -> {
            log.error("First page of chapter not found. ChapterId={}", chapterId);
            return new PageNotFoundException("Page for this chapter not found", chapterId);
        });

        return pageMapper.toDto(page);
    }
}
