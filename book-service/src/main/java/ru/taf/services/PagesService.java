package ru.taf.services;

import ru.taf.dto.PageDTO;

import java.util.List;

public interface PagesService {

    PageDTO findPageByNumber(Integer bookId, int pageNumber);

    List<PageDTO> findAllPagesByBook(int bookId);

    PageDTO getNextPage(Integer pageId);

    PageDTO getPrevPage(Integer pageId);

    PageDTO getFirstPage(Integer chapterId);
}