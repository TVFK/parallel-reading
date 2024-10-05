package ru.taf.services;

import ru.taf.entities.Page;

import java.util.List;

public interface PagesService {

    Page findPageByNumber(int bookId, int pageNumber);

    List<Page> findAllPagesByBook(int bookId);
}