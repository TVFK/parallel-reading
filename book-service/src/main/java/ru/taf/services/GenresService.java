package ru.taf.services;

import ru.taf.dto.GenreDTO;
import ru.taf.entities.Genre;

import java.util.List;

public interface GenresService {
    List<GenreDTO> getAllGenres();
}
