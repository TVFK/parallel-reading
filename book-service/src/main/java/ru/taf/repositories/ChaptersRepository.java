package ru.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.taf.entities.Chapter;

public interface ChaptersRepository extends JpaRepository<Chapter, Integer> {
}
