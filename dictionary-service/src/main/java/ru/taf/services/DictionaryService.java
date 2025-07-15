package ru.taf.services;

import ru.taf.dto.DictionaryCardDTO;
import ru.taf.dto.NewDictionaryCardDTO;
import ru.taf.dto.UpdateDictionaryCardDTO;

import java.util.List;

public interface DictionaryService {

    DictionaryCardDTO getDictionaryCardById(Integer cardId);

    List<DictionaryCardDTO> getUserDictCards(String userId);

    DictionaryCardDTO createDictionaryCard(String userId, NewDictionaryCardDTO dictionaryCardDTO);

    void updateDictCard(String userId, Integer cardId, UpdateDictionaryCardDTO dictionaryCardDTO);

    void deleteCard(String userId, Integer cardId);
}
