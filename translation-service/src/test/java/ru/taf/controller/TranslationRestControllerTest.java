package ru.taf.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.taf.dto.DefaultJsonTranslationResponse;
import ru.taf.service.TranslationService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TranslationRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class TranslationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslationService translationService;

    @Test
    void getTranslation_ShouldReturnTranslation() throws Exception {
        // Создаем тестовые данные с правильными параметрами
        var response = new DefaultJsonTranslationResponse(
                List.of(),
                200
        );

        when(translationService.getTranslation(anyString(), any(), anyInt()))
                .thenReturn(response);

        mockMvc.perform(get("/translations")
                        .param("text", "hello")
                        .param("ui", "en")
                        .param("flags", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(translationService).getTranslation("hello", "en", 1);
    }

    @Test
    void getTranslation_WithoutOptionalParams_ShouldReturnTranslation() throws Exception {
        var response = new DefaultJsonTranslationResponse(List.of(), 200);

        when(translationService.getTranslation(anyString(), any(), any()))
                .thenReturn(response);

        mockMvc.perform(get("/translations")
                        .param("text", "test"))
                .andExpect(status().isOk());

        verify(translationService).getTranslation(eq("test"), isNull(), isNull());
    }
}