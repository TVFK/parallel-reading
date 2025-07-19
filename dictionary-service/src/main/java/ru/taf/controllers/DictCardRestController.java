package ru.taf.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.taf.dto.DictionaryCardDTO;
import ru.taf.dto.UpdateDictionaryCardDTO;
import ru.taf.services.DictionaryService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dictionary/{cardId}")
public class DictCardRestController {

    private final DictionaryService dictionaryService;

    @GetMapping
    public ResponseEntity<DictionaryCardDTO> getDictCardById(
            @PathVariable("cardId") UUID cardId
    ) {
        DictionaryCardDTO dictCard = dictionaryService.getDictionaryCardById(cardId);
        return ResponseEntity.ok(dictCard);
    }

    @PatchMapping
    public ResponseEntity<?> updateDictionaryCard(
            @PathVariable("cardId") UUID cardId,
            @Valid @RequestBody UpdateDictionaryCardDTO card,
            BindingResult bindingResult,
            @AuthenticationPrincipal Jwt jwt
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        String userId = jwt.getSubject();
        dictionaryService.updateDictCard(userId, cardId, card);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteDictionaryCard(
            @PathVariable("cardId") UUID cardId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        dictionaryService.deleteCard(jwt.getSubject(), cardId);
        return ResponseEntity.noContent().build();
    }
}
