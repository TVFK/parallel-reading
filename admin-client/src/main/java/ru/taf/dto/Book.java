package ru.taf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record Book (
        int id,
        String title,
        String author,
        String imageUrl,
        String level,
        String publishedYear,
        int numberOfPage,
        String description,
        String image
){
}
