package ru.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.taf.entities.Genre;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Integer> {
}
