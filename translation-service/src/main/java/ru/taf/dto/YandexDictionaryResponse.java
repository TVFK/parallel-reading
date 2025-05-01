package ru.taf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import java.util.List;

public record YandexDictionaryResponse(
        Head head,
        List<Definition> def,
        int nmt_code,
        int code
) {
    public record Head() {}

    public record Definition(
            String text,
            String pos,
            String ts,
            List<Translation> tr
    ) {}

    public record Translation(
            String text,
            String pos,
            String asp,
            String gen,
            int fr,
            List<Synonym> syn,
            List<Meaning> mean
    ) {}

    public record Synonym(
            String text,
            String pos,
            String asp,
            int fr
    ) {}

    public record Meaning(
            String text
    ) {}
}
