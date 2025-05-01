package ru.taf.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.taf.dto.GenreDTO;
import ru.taf.mappers.GenreMapper;
import ru.taf.services.GenresService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenresRestController {

    private final GenresService genresService;

    @GetMapping
    public List<GenreDTO> getGenres() {
        return genresService.getAllGenres();
    }
}
