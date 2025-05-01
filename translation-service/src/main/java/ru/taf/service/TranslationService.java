package ru.taf.service;

import ru.taf.dto.DefaultJsonTranslationResponse;

public interface TranslationService {
    DefaultJsonTranslationResponse getTranslation(String text, String ui, Integer flags);
}
