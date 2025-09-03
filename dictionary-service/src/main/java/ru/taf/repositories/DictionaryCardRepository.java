package ru.taf.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.taf.entities.DictionaryCard;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DictionaryCardRepository extends JpaRepository<DictionaryCard, UUID> {
    Page<DictionaryCard> findAllByUserId(UUID userId, Pageable pageable);
    Page<DictionaryCard> findByUserIdAndNextReviewDateLessThanEqual(UUID userId, LocalDate date, Pageable pageable);
}
