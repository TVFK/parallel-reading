package ru.taf.dto;

import java.util.UUID;

public record ReviewCardDTO (
        UUID cardId,
        int difficulty
) {
}
