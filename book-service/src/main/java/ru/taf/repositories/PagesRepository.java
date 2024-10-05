package ru.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.taf.entities.Page;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagesRepository extends JpaRepository<Page, Integer> {

    @Query(nativeQuery = true, value = """
            select * from pages p
                join chapters c on c.id=p.chapter_id
                    join books b on c.book_id=b.id
                        where page_number=?1 and book_id=?2 ;""")
    Optional<Page> findPageByPageNumberAndBookId(int pageNumber, int bookId);

    @Query(nativeQuery = true, value = """
            select * from pages p
                join chapters c on c.id=p.chapter_id
                    join books b on c.book_id=b.id;""")
    List<Page> findAllByBookId(int bookId);
}