package ru.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.taf.entities.Genre;

public interface GenresRepository extends JpaRepository<Genre, Integer> {
}
