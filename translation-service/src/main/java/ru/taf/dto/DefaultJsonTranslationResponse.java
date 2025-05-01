package ru.taf.dto;

import java.util.List;

public record DefaultJsonTranslationResponse(
        List<WordDefinition> definitions,
        int code
) {
    public record WordDefinition(
            String text,
            String pos,
            String ts,
            List<Translation> translations
    ) {}

    public record Translation(
            String text,
            String pos,
            String asp,
            String gen,
            int fr,
            List<String> meanings
    ) {}
}
