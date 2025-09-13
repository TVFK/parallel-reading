package ru.taf.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.taf.dto.YandexDictionaryResponse;
import ru.taf.exceptions.ApiKeyBlockedException;
import ru.taf.exceptions.DailyLimitExceededException;
import ru.taf.exceptions.TextTooLongException;
import ru.taf.exceptions.YandexDictionaryException;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class RestClientYandexDictionaryClient implements YandexDictionaryClient {

    private final RestClient restClient;
    private final String apiKey;
    private final String lang;

    @Override
    public YandexDictionaryResponse getJsonTranslation(String text, String ui, Integer flags) {

        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/dicservice.json/lookup")
                        .queryParam("key", apiKey)
                        .queryParam("text", text)
                        .queryParam("lang", lang)
                        .queryParamIfPresent("ui", Optional.ofNullable(ui))
                        .queryParamIfPresent("flags", Optional.ofNullable(flags))
                        .build())
                .retrieve()
                .onStatus(status -> status.value() == 402, (req, res) -> {
                    log.error("Api key blocked. Status={}, Request={}, Response={}", res.getStatusCode(), req, res);
                    throw new ApiKeyBlockedException();
                })
                .onStatus(status -> status.value() == 403, (req, res) -> {
                    log.error("Api key blocked. Status={}, Request={}, Response={}", res.getStatusCode(), req, res);
                    throw new DailyLimitExceededException();
                })
                .onStatus(status -> status.value() == 413, (req, res) -> {
                    log.error("Api key blocked. Status={}, Request={}, Response={}", res.getStatusCode(), req, res);
                    throw new TextTooLongException();
                })
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    String body = new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8);
                    log.error("Api key blocked. Status={}, Request={}, Response={}", res.getStatusCode(), req, res);
                    throw new YandexDictionaryException(res.getStatusCode(), body);
                })
                .body(YandexDictionaryResponse.class);
    }
}
