package ru.taf.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.taf.dto.DictionaryCardDTO;
import ru.taf.dto.mappers.DictionaryCardMapper;
import ru.taf.entities.DictionaryCard;
import ru.taf.exceptions.DictionaryCardNotFoundException;
import ru.taf.repositories.DictionaryCardRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DictionaryServiceImplTest {
    @Mock
    DictionaryCardRepository cardRepository;
    @Mock
    DictionaryCardMapper cardMapper;
    @InjectMocks
    DictionaryServiceImpl dictionaryService;

    @Test
    void getDictionaryCardById_CardExist_ReturnsCard(){
        // given
        var id = UUID.randomUUID();
        var card = new DictionaryCard();
        var dto = new DictionaryCardDTO(null, null, null, null, null, null, null, null);

        doReturn(card)
                .when(cardRepository)
                .findById(id);

        doReturn(dto)
                .when(cardMapper)
                .toDto(card);
        // when
        var result = dictionaryService.getDictionaryCardById(id);

        // then
        assertEquals(result, dto);

        verify(cardRepository).findById(id);
        verify(cardMapper).toDto(card);
        verifyNoMoreInteractions(cardRepository);
        verifyNoMoreInteractions(cardMapper);
    }

    @Test
    void getDictionaryCardById_CardDoesNotExist_ReturnsDictionaryCardNotFoundException(){
        // given
        var id = UUID.randomUUID();
        // when
        var exception = assertThrows(DictionaryCardNotFoundException.class, () ->
                dictionaryService.getDictionaryCardById(id));

        // then
        assertNotNull(exception.getMessage());

        verify(cardRepository).findById(id);
        verifyNoMoreInteractions(cardRepository);
        verifyNoInteractions(cardMapper);
    }
}