package ru.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.taf.dto.PageDTO;
import ru.taf.entities.Page;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagesRepository extends JpaRepository<Page, Integer> {

    @Query(nativeQuery = true, value = """
            select p.id, p.chapter_id, p.page_number, s.sentence_index, s.original_text, s.translated_text
            from page p
            join sentence s on s.page_id = p.id
            join chapter c on c.id = p.chapter_id
            join book b on c.book_id = b.id
            where p.page_number = ?2 and b.id = ?1
            """)
    Optional<Page> findPageByPageNumberAndBookId(Integer bookId, Integer pageNumber);

    @Query(nativeQuery = true, value = """
            select p.id, p.chapter_id, p.page_number
            from page p
            join chapter c on c.id = p.chapter_id
            join book b on c.book_id = b.id
            where b.id = ?1
            """)
    List<Page> findAllPagesByBookId(int bookId);

    Optional<Page> findFirstByChapter_Id(Integer chapterId);
}