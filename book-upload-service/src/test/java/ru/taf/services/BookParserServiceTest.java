package ru.taf.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookParserServiceTest {

    @Test
    void test(){
        int c = 3 + 4;
        assertEquals(c, 7);
    }
}