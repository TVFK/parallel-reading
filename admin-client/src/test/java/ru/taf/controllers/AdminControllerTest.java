package ru.taf.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void index_ShouldReturnBooksIndexView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/index"));
    }

    @Test
    void index_WithAdminPath_ShouldReturnBooksIndexView() throws Exception {
        // Если контроллер размещен по пути /admin
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/index"));
    }
}