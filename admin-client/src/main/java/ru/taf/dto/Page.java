package ru.taf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Page {

    private int id;

    private Chapter chapter;

    private int pageNumber;

    private String content;
}