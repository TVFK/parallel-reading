package ru.taf.dto;

import java.util.List;

public record Chapter(
        String title,
        int chapterOrder,
        List<Page> pages
) {
}
