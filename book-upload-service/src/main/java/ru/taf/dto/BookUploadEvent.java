package ru.taf.dto;

public record BookUploadEvent(
        int id,
        String originalTextKey,
        String translatedTextKey
) {
}
