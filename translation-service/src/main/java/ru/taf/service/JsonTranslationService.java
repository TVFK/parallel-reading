package ru.taf.service;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.taf.client.YandexDictionaryClient;
import ru.taf.dto.DefaultJsonTranslationResponse;
import ru.taf.dto.YandexDictionaryResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JsonTranslationService implements TranslationService {

    private final YandexDictionaryClient yandexDictClient;
    private final StanfordCoreNLP pipeline;

    @Override
    @Cacheable(value = "translation", key = "#text")
    public DefaultJsonTranslationResponse getTranslation(String text, String ui, Integer flags) {

        log.info("Start get translation. Word={}, ui={}, flags={}", text, ui, flags);

        String lemma = getLemma(text);
        log.info("Successfully get lemma. Word={}, Lemma={}", text, lemma);

        YandexDictionaryResponse yandexResponse = yandexDictClient.getJsonTranslation(lemma, ui, flags);
        log.info("Translation get successfully. Word={}, Translation={}", text, yandexResponse);

        return mapToDefaultResponse(yandexResponse);
    }

    // Only for regular verb in the past tense and plural words
    public String getLemma(String text) {
        if (text == null || text.trim().isEmpty()) return text;

        CoreDocument doc = new CoreDocument(text);
        try {
            pipeline.annotate(doc);
            return doc.tokens().stream()
                    .map(token -> {
                        String lemma = token.lemma().toLowerCase();
                        return isValidWord(token) ? lemma : token.word();
                    })
                    .collect(Collectors.joining(" "));
        } catch (Exception e) {
            log.error("Error while getting lemma. Word={}", text, e);
            return text;
        }
    }

    private boolean isValidWord(CoreLabel label) {
        String pos = label.tag();

        boolean isPastVerb = pos.equals("VBD");
        boolean isPlural = pos.matches("NNS|NNPS");

        return isPlural || isPastVerb;
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