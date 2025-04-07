package ru.taf.services;

import ru.taf.entities.Page;

import java.util.List;

public interface PagesService {

    Page findPageByNumber(Integer bookId, int pageNumber);

    List<Page> findAllPagesByBook(int bookId);

    Page getNextPage(Integer pageId);

    Page getPrevPage(Integer pageId);
}