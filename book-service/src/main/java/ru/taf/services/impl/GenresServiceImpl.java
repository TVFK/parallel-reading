package ru.taf.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.entities.Genre;
import ru.taf.repositories.GenresRepository;
import ru.taf.services.GenresService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenresServiceImpl implements GenresService {

    private final GenresRepository genresRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genresRepository.findAll();
    }
}
