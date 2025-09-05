package ru.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.taf.entities.Chapter;

import java.util.List;

@Repository
public interface ChaptersRepository extends JpaRepository<Chapter, Integer> {

    List<Chapter> findAllByBook_Id(Integer bookId);
}
