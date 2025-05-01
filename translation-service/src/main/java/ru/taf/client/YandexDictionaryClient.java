package ru.taf.client;

import ru.taf.dto.YandexDictionaryResponse;

public interface YandexDictionaryClient {

    YandexDictionaryResponse getJsonTranslation(String text, String ui, Integer flags);
}
