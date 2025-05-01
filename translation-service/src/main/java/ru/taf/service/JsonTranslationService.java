package ru.taf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.taf.client.YandexDictionaryClient;
import ru.taf.dto.DefaultJsonTranslationResponse;
import ru.taf.dto.YandexDictionaryResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JsonTranslationService implements TranslationService {

    private final YandexDictionaryClient yandexDictClient;

    @Override
    @Cacheable(value = "translation", key = "#text")
    public DefaultJsonTranslationResponse getTranslation(String text, String ui, Integer flags) {
        YandexDictionaryResponse yandexResponse = yandexDictClient.getJsonTranslation(text, ui, flags);
        return mapToDefaultResponse(yandexResponse);
    }

    private DefaultJsonTranslationResponse mapToDefaultResponse(YandexDictionaryResponse yandexResponse) {
        if (yandexResponse.def() == null || yandexResponse.def().isEmpty()) {
            return new DefaultJsonTranslationResponse(
                    Collections.emptyList(),
                    yandexResponse.code()
            );
        }

        List<DefaultJsonTranslationResponse.WordDefinition> definitions = yandexResponse.def().stream()
                .map(this::mapDefinition)
                .collect(Collectors.toList());

        return new DefaultJsonTranslationResponse(
                definitions,
                yandexResponse.code()
        );
    }

    private DefaultJsonTranslationResponse.WordDefinition mapDefinition(YandexDictionaryResponse.Definition def) {
        return new DefaultJsonTranslationResponse.WordDefinition(
                def.text(),
                def.pos(),
                def.ts(),
                def.tr() != null ? def.tr().stream()
                        .map(this::mapTranslation)
                        .collect(Collectors.toList()) : Collections.emptyList()
        );
    }

    private DefaultJsonTranslationResponse.Translation mapTranslation(YandexDictionaryResponse.Translation tr) {
        return new DefaultJsonTranslationResponse.Translation(
                tr.text(),
                tr.pos(),
                tr.asp(),
                tr.gen(),
                tr.fr(),
                tr.mean() != null ? tr.mean().stream()
                        .map(YandexDictionaryResponse.Meaning::text)
                        .collect(Collectors.toList()) : Collections.emptyList()
        );
    }
}