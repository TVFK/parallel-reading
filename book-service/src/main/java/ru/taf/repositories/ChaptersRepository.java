package ru.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.taf.entities.Chapter;

import java.util.List;

public interface ChaptersRepository extends JpaRepository<Chapter, Integer> {

    List<Chapter> findAllByBook_Id(Integer bookId);
}
