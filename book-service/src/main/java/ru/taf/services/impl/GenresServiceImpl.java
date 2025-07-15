package ru.taf.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.dto.GenreDTO;
import ru.taf.dto.mappers.GenreMapper;
import ru.taf.repositories.GenresRepository;
import ru.taf.services.GenresService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenresServiceImpl implements GenresService {

    private final GenresRepository genresRepository;
    private final GenreMapper genreMapper;

    @Override
    public List<GenreDTO> getAllGenres() {
        return genresRepository.findAll()
                .stream()
                .map(genreMapper::toDto)
                .toList();
    }
}
