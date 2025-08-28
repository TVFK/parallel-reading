package ru.taf.dto;

public record BookUploadEvent(
        int bookId,
        String originalTextKey,
        String translatedTextKey
) {
}
