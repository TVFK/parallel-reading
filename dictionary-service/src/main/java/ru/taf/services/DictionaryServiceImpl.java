package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.taf.dto.DictionaryCardDTO;
import ru.taf.dto.NewDictionaryCardDTO;
import ru.taf.dto.ReviewCardDTO;
import ru.taf.dto.UpdateDictionaryCardDTO;
import ru.taf.dto.mappers.DictionaryCardMapper;
import ru.taf.entities.DictionaryCard;
import ru.taf.entities.User;
import ru.taf.exceptions.AccessDeniedException;
import ru.taf.exceptions.DictionaryCardNotFoundException;
import ru.taf.repositories.DictionaryCardRepository;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryCardRepository cardRepository;

    private final DictionaryCardMapper cardMapper;

    @Override
    public DictionaryCardDTO getDictionaryCardById(UUID cardId) {
        DictionaryCard dictionaryCard = cardRepository.findById(cardId).orElseThrow(() ->
                new DictionaryCardNotFoundException("dictionary_card.not_found"));

        return cardMapper.toDto(dictionaryCard);
    }

    @Override
    public Page<DictionaryCardDTO> getUserDictCards(UUID userId, int page, int size, String sortBy) {
        Sort sort = switch (sortBy) {
            case "alphabet" -> Sort.by("word").ascending();
            case "review" -> Sort.by("nextReviewDate").ascending();
            case "difficulty" -> Sort.by("difficulty").descending();
            default -> Sort.by("createdAt").descending();
        };

        Pageable pageable = PageRequest.of(page, size, sort);
        return cardRepository.findAllByUserId(userId, pageable).map(cardMapper::toDto);
    }

    @Override
    public Page<DictionaryCardDTO> getCardsForReview(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cardRepository.findByUserIdAndNextReviewDateLessThanEqual(userId, LocalDate.now(), pageable)
                .map(cardMapper::toDto);
    }

    @Override
    @Transactional
    public DictionaryCardDTO createDictionaryCard(UUID userId, NewDictionaryCardDTO dictionaryCardDTO) {
        User user = new User();
        user.setId(userId);

        DictionaryCard dictionaryCard = cardMapper.toEntity(dictionaryCardDTO);
        dictionaryCard.setUser(user);
        dictionaryCard.setCreatedAt(LocalDate.now());

        dictionaryCard.setTags(dictionaryCardDTO.tags());
        dictionaryCard.setRepetitionCount(0);
        dictionaryCard.setEaseFactor(2.5);
        dictionaryCard.setIntervalDays(1);
        dictionaryCard.setNextReviewDate(LocalDate.now());

        DictionaryCard createdDictCard = cardRepository.save(dictionaryCard);

        return cardMapper.toDto(createdDictCard);
    }

    @Override
    @Transactional
    public void updateDictCard(UUID userId, UUID cardId, UpdateDictionaryCardDTO dictionaryCardDTO) {

        cardRepository.findById(cardId)
                .ifPresentOrElse(card -> {
                    if (!card.getUser().getId().equals(userId)) {
                        throw new AccessDeniedException("You don't have permission to perform this action");
                    }

                    card.setWord(dictionaryCardDTO.word());
                    card.setTranslation(dictionaryCardDTO.translation());
                    card.setContext(dictionaryCardDTO.context());
                    card.setDifficulty(dictionaryCardDTO.difficulty());
                    card.setTags(dictionaryCardDTO.tags());

                }, () -> {
                    throw new DictionaryCardNotFoundException("dictionary_card.not_found");
                });
    }

    @Override
    @Transactional
    public void deleteCard(UUID userId, UUID cardId) {
        DictionaryCard card = cardRepository.findById(cardId).orElseThrow(() ->
                new DictionaryCardNotFoundException("dictionary_card.not_found")
        );
        if (!card.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to perform this action");
        }
        cardRepository.deleteById(cardId);
    }

    @Override
    @Transactional
    public void reviewCard(UUID userId, ReviewCardDTO reviewDTO) {
        DictionaryCard card = cardRepository.findById(reviewDTO.cardId())
                .orElseThrow(() -> new DictionaryCardNotFoundException("dictionary_card.not_found"));

        if (!card.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to perform this action");
        }

        card.setDifficulty(reviewDTO.difficulty());

        updateCardWithSM2(card);

        cardRepository.save(card);
    }

    private void updateCardWithSM2(DictionaryCard card) {
        int difficulty = card.getDifficulty();
        int repetitions = card.getRepetitionCount();
        double easeFactor = card.getEaseFactor();
        int interval = card.getIntervalDays();

        if (difficulty < 2) {
            repetitions = 0;
            interval = 1;
        } else {
            easeFactor = Math.max(1.3, easeFactor + (0.1 - (3 - difficulty) * (0.08 + (3 - difficulty) * 0.02)));

            if (repetitions == 0) {
                interval = 1;
            } else if (repetitions == 1) {
                interval = 3;
            } else {
                interval = (int) Math.ceil(interval * easeFactor);
            }

            repetitions++;
        }

        card.setRepetitionCount(repetitions);
        card.setEaseFactor(easeFactor);
        card.setIntervalDays(interval);
        card.setNextReviewDate(LocalDate.now().plusDays(interval));
    }
}
