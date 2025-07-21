package ru.taf.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.taf.client.exception.BadRequestException;
import ru.taf.client.exception.BookNotFoundException;
import ru.taf.dto.Book;
import ru.taf.dto.NewBookDTO;
import ru.taf.dto.UpdateBookDTO;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class BooksRestClientImpl implements BooksRestClient {

    private final RestClient restClient;

    @Override
    public List<Book> findAllBooks(String filter) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("/books")
                .queryParam("title", filter);

        return restClient.get()
                .uri(uri.toUriString())
                .retrieve()
                .body(new ParameterizedTypeReference<List<Book>>() {});
    }

    @Override
    public Book findBook(Integer bookId) {
        try {
            return restClient.get()
                    .uri("books/{bookId}", bookId)
                    .retrieve()
                    .body(Book.class);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new BookNotFoundException("book with id %d not found".formatted(bookId));
        }
    }

    // TODO дохуя всего сделать...
    @Override
    public Book createBook(NewBookDTO book) {
        try{
            return restClient.post()
                    .uri("books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(book)
                    .retrieve()
                    .body(Book.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            List<String> errors = (List<String>) (problemDetail != null ? Objects.requireNonNull(problemDetail.getProperties()).get("errors") : List.of("Unknown error"));
            throw new BadRequestException(errors);
        }
    }

    @Override
    public void updateBook(UpdateBookDTO book, Integer bookId) {
        try {
            restClient.patch()
                    .uri("books/{bookId}", bookId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(book)
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
