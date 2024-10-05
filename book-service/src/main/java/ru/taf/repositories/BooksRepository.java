package ru.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.taf.entities.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
