package ru.taf.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.taf.exceptions.BadRequestException;
import ru.taf.exceptions.BookNotFoundException;
import ru.taf.dto.BookDTO;
import ru.taf.dto.NewBookDTO;
import ru.taf.dto.UpdateBookDTO;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class BooksRestClientImpl implements BooksRestClient {

    private final RestClient restClient;

    @Override
    public List<BookDTO> findAllBooks(String filter) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("/books")
                .queryParam("title", filter);

        return restClient.get()
                .uri(uri.toUriString())
                .retrieve()
                .body(new ParameterizedTypeReference<List<BookDTO>>() {});
    }

    @Override
    public BookDTO findBook(Integer bookId) {
        try {
            return restClient.get()
                    .uri("books/{bookId}", bookId)
                    .retrieve()
                    .body(BookDTO.class);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new BookNotFoundException("book with id %d not found".formatted(bookId));
        }
    }

    @Override
    public BookDTO createBook(NewBookDTO book) {
        try{
            return restClient.post()
                    .uri("books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(book)
                    .retrieve()
                    .body(BookDTO.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            List<String> errors = (List<String>) (problemDetail != null ? Objects.requireNonNull(problemDetail.getProperties()).get("errors") : List.of("Unknown error"));
            throw new BadRequestException(errors);
        }
    }

    @Override
    public void updateBook(BookDTO book, Integer bookId) {
        UpdateBookDTO updateBookDTO = new UpdateBookDTO(
                book.getTitle(),
                book.getAuthor(),
                book.getPublishedYear(),
                book.getLevel(),
                book.getDescription()
        );
        try {
            restClient.patch()
                    .uri("books/{bookId}", bookId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updateBookDTO)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            List<String> errors = (List<String>) (problemDetail != null ? Objects.requireNonNull(problemDetail.getProperties()).get("errors") : List.of("Unknown error"));
            throw new BadRequestException(errors);
        }
    }

    @Override
    public void deleteBook(Integer bookId) {
        try {
            restClient.delete()
                    .uri("/books/{bookId}", bookId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new BookNotFoundException("Book with id: %d not found".formatted(bookId));
        }
    }
}
