package ru.taf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Chapter {

    private int id;

    private Book book;

    private int chapterNumber;

    private String title;

    private List<Page> pages;
}
