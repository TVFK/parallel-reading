package ru.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.taf.entities.DictionaryCard;
import java.util.List;

@Repository
public interface DictionaryCardRepository extends JpaRepository<DictionaryCard, Integer> {

    List<DictionaryCard> findAllByUserId(String userId); // fixme мб не будет работать
}
