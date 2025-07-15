package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.dto.DictionaryCardDTO;
import ru.taf.dto.NewDictionaryCardDTO;
import ru.taf.dto.UpdateDictionaryCardDTO;
import ru.taf.dto.mappers.DictionaryCardMapper;
import ru.taf.entities.DictionaryCard;
import ru.taf.entities.User;
import ru.taf.exceptions.AccessDeniedException;
import ru.taf.exceptions.DictionaryCardNotFoundException;
import ru.taf.repositories.DictionaryCardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryCardRepository cardRepository;

    private final DictionaryCardMapper cardMapper;

    @Override
    public DictionaryCardDTO getDictionaryCardById(Integer cardId) {
        DictionaryCard dictionaryCard = cardRepository.findById(cardId).orElseThrow(() ->
                new DictionaryCardNotFoundException("dictionary_card.not_found"));

        return cardMapper.toDto(dictionaryCard);
    }

    @Override
    public List<DictionaryCardDTO> getUserDictCards(String userId) {
        List<DictionaryCard> dictionaryCardList = cardRepository.findAllByUserId(userId);

        return dictionaryCardList.stream()
                .map(cardMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public DictionaryCardDTO createDictionaryCard(String userId, NewDictionaryCardDTO dictionaryCardDTO) {
        User user = new User();
        user.setId(userId);

        DictionaryCard dictionaryCard = cardMapper.toEntity(dictionaryCardDTO);
        dictionaryCard.setUser(user);

        DictionaryCard createdDictCard = cardRepository.save(dictionaryCard);

        return cardMapper.toDto(createdDictCard);
    }

    @Override
    @Transactional
    public void updateDictCard(String userId, Integer cardId, UpdateDictionaryCardDTO dictionaryCardDTO) {

        cardRepository.findById(cardId)
                .ifPresentOrElse(card -> {
                    if(!card.getUser().getId().equals(userId)){
                        throw new AccessDeniedException("You don't have permission to perform this action");
                    }

                    card.setWord(dictionaryCardDTO.word());
                    card.setTranslation(dictionaryCardDTO.translation());
                    card.setContext(dictionaryCardDTO.context());

                }, () -> {
                    throw new DictionaryCardNotFoundException("dictionary_card.not_found");
                });
    }

    @Override
    @Transactional
    public void deleteCard(String userId, Integer cardId) {
        DictionaryCard card = cardRepository.findById(cardId).orElseThrow(() ->
                new DictionaryCardNotFoundException("dictionary_card.not_found")
        );
        if (!card.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to perform this action");
        }
        cardRepository.deleteById(cardId);
    }
}
