package ru.taf.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.taf.dto.mappers.BookMapper;
import ru.taf.exceptions.BookNotFoundException;
import ru.taf.repositories.BooksRepository;
import ru.taf.services.impl.BooksServiceImpl;
import ru.taf.util.DataUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BooksServiceImplTest {

    @InjectMocks
    BooksServiceImpl booksService;

    @Mock
    BooksRepository booksRepository;

    @Mock
    BookMapper bookMapper;

    @Test
    void getBooks_AllFiltersIsNull_ReturnsAllBooksSortedAsc() {
        // given
        var book = DataUtils.getBook();
        var bookDTO1 = DataUtils.getBookDTO();

        doReturn(List.of(book))
                .when(booksRepository)
                        .findAll(any(Specification.class), eq(Sort.by("publishedYear").ascending()));

        doReturn(bookDTO1)
                .when(bookMapper)
                .toDto(book);

        // when
        var result = booksService.getBooks(null, null, null, null);

        // then
        assertEquals(result, List.of(bookDTO1));

        verify(booksRepository).findAll(any(Specification.class), eq(Sort.by("publishedYear").ascending()));
        verify(bookMapper).toDto(book);
        verifyNoMoreInteractions(booksRepository);
        verifyNoMoreInteractions(bookMapper);
    }

    @Test
    void getBookById_BookExist_ReturnsBookDTO() {
        // given
        var book = DataUtils.getBook();
        var bookDTO = DataUtils.getBookDTO();

        doReturn(Optional.of(book))
                .when(booksRepository)
                .findById(1);

        doReturn(bookDTO)
                .when(bookMapper)
                .toDto(book);

        // when
        var result = booksService.getBookById(1);

        // then
        assertEquals(bookDTO, result);

        verify(booksRepository).findById(1);
        verify(bookMapper).toDto(book);
        verifyNoMoreInteractions(booksRepository);
        verifyNoMoreInteractions(bookMapper);
    }

    @Test
    void getBookById_BookDoesNotExist_ReturnsBookNotFoundException() {
        // given
        // when
        var exception = assertThrows(BookNotFoundException.class, () -> booksService.getBookById(4));

        // then
        assertNotNull(exception.getMessage());

        verify(booksRepository).findById(4);
        verifyNoMoreInteractions();
        verifyNoInteractions(bookMapper);
    }

    @Test
    void deleteBook() {
        // given
        // when
        booksService.deleteBook(1);

        // then
        verify(booksRepository).deleteById(1);
        verifyNoMoreInteractions(booksRepository);
    }
}