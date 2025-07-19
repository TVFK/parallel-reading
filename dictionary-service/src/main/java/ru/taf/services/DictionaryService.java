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

    Page<DictionaryCardDTO> getUserDictCards(String userId, int page, int size, String sortBy);

    Page<DictionaryCardDTO> getCardsForReview(String userId, int page, int size);

    DictionaryCardDTO createDictionaryCard(String userId, NewDictionaryCardDTO dictionaryCardDTO);

    void updateDictCard(String userId, UUID cardId, UpdateDictionaryCardDTO dictionaryCardDTO);

    void deleteCard(String userId, UUID cardId);

    void reviewCard(String userId, ReviewCardDTO reviewDTO);
}
