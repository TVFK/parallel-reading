package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.taf.entities.Genre;
import ru.taf.repositories.GenresRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenresServiceImpl implements GenresService {

    private final GenresRepository genresRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genresRepository.findAll();
    }
}
