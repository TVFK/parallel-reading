package ru.taf.services;

import org.springframework.data.domain.Page;
import ru.taf.dto.DictionaryCardDTO;
import ru.taf.dto.NewDictionaryCardDTO;
import ru.taf.dto.ReviewCardDTO;
import ru.taf.dto.UpdateDictionaryCardDTO;

import java.util.List;
import java.util.UUID;

public interface DictionaryService {

    DictionaryCardDTO getDictionaryCardById(UUID cardId);

    Page<DictionaryCardDTO> getUserDictCards(UUID userId, int page, int size, String sortBy);

    Page<DictionaryCardDTO> getCardsForReview(UUID userId, int page, int size);

    DictionaryCardDTO createDictionaryCard(UUID userId, NewDictionaryCardDTO dictionaryCardDTO);

    void updateDictCard(UUID userId, UUID cardId, UpdateDictionaryCardDTO dictionaryCardDTO);

    void deleteCard(UUID userId, UUID cardId);

    void reviewCard(UUID userId, ReviewCardDTO reviewDTO);
}
