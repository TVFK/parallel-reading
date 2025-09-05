package ru.taf.util;

import ru.taf.dto.BookDTO;
import ru.taf.dto.GenreDTO;
import ru.taf.entities.Book;
import ru.taf.entities.Chapter;
import ru.taf.entities.Genre;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataUtils {

    public static List<Book> getListOfBooks() {
        return IntStream.range(1, 5)
                .mapToObj(i -> new Book(
                        i,
                        "title %d".formatted(i),
                        "author %d".formatted(i),
                        UUID.randomUUID(),
                        "198%d".formatted(i),
                        "C1",
                        "description %d".formatted(i),
                        i,
                        null,
                        null
                )).toList();
    }

    public static List<BookDTO> getListOfBooksDTO() {
        return IntStream.range(1, 5)
                .mapToObj(i -> new BookDTO(
                        i,
                        "title %d".formatted(i),
                        "author %d".formatted(i),
                        UUID.randomUUID(),
                        "198%d".formatted(i),
                        "C1",
                        "description %d".formatted(i),
                        i,
                        null
                )).toList();
    }

    public static Book getBook() {
        return Book.builder()
                .id(1)
                .title("title")
                .author("author")
                .imageKey(UUID.randomUUID())
                .publishedYear("1982")
                .level("C1")
                .description("description")
                .numberOfPage(333)
                .genres(IntStream.range(1, 3)
                        .mapToObj(i -> new Genre(i, "genre %d".formatted(i), null))
                        .collect(Collectors.toSet()))
                .chapters(IntStream.range(1, 3)
                        .mapToObj(i -> new Chapter(i, null, i, "title %d".formatted(i), null)).toList())
                .build();
    }

    public static BookDTO getBookDTO() {
        return BookDTO.builder()
                .id(1)
                .title("title")
                .author("author")
                .imageKey(UUID.randomUUID())
                .publishedYear("1982")
                .level("C1")
                .description("description")
                .numberOfPage(333)
                .genres(IntStream.range(1, 3)
                        .mapToObj(i -> new GenreDTO(i, "genre %d".formatted(i)))
                        .collect(Collectors.toSet()))
                .build();
    }
}
