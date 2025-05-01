package ru.taf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.taf.dto.DefaultJsonTranslationResponse;
import ru.taf.service.JsonTranslationService;
import ru.taf.service.TranslationService;

@RestController
@RequestMapping("/translations")
@RequiredArgsConstructor
public class TranslationRestController {

    private final TranslationService translationService;

    @GetMapping
    public ResponseEntity<DefaultJsonTranslationResponse> getTranslation(
            @RequestParam(value = "text") String text,
            @RequestParam(value = "ui", required = false) String ui,
            @RequestParam(value = "flags", required = false) Integer flags
    ){
        DefaultJsonTranslationResponse translation = translationService.getTranslation(text, ui, flags);

        return ResponseEntity.ok(translation);
    }
}
