package ru.taf.dto;

import java.io.Serializable;

public record GenreDTO(
        int id,
        String name
) implements Serializable {
}
