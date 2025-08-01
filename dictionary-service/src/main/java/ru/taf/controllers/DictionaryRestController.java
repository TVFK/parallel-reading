package ru.taf.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.taf.dto.DictionaryCardDTO;
import ru.taf.dto.NewDictionaryCardDTO;
import ru.taf.dto.ReviewCardDTO;
import ru.taf.services.DictionaryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dictionary")
public class DictionaryRestController {

    private final DictionaryService dictionaryService;

    @GetMapping
    public ResponseEntity<Page<DictionaryCardDTO>> getUserCards(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sort) {

        String userId = jwt.getSubject();
        return ResponseEntity.ok(dictionaryService.getUserDictCards(userId, page, size, sort));
    }

    @PostMapping
    public ResponseEntity<?> createDictionaryCard(
            @Valid @RequestBody NewDictionaryCardDTO card,
            BindingResult bindingResult,
            UriComponentsBuilder uriComponentsBuilder,
            @AuthenticationPrincipal Jwt jwt
    ) throws BindException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        String userId = jwt.getSubject();
        DictionaryCardDTO createdDictCard = dictionaryService.createDictionaryCard(userId, card);

        // TODO возможно, стоит поменять url к карточке, если появится такой путь
        return ResponseEntity.created(
                uriComponentsBuilder.path("/dictionary")
                        .build().toUri()
        ).body(createdDictCard);
    }

    @GetMapping("/review")
    public ResponseEntity<Page<DictionaryCardDTO>> getUserReviewCards(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        String userId = jwt.getSubject();
        return ResponseEntity.ok(dictionaryService.getCardsForReview(userId, page, size));
    }
    @PostMapping("/review")
    public ResponseEntity<Void> reviewCard(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody ReviewCardDTO reviewCardDTO
    ) {
        dictionaryService.reviewCard(jwt.getSubject(), reviewCardDTO);
        return ResponseEntity.noContent().build();
    }
}
