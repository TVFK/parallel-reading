package ru.taf.dto;

import java.io.Serializable;

public record BookUploadEvent(
        int id,
        String originalTextKey,
        String translatedTextKey
) implements Serializable {
}
