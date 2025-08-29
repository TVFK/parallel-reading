package ru.taf.dto;

import java.io.Serializable;

public record BookUploadEvent(
        int bookId,
        String originalTextKey,
        String translatedTextKey
) implements Serializable {
}
