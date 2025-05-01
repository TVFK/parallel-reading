package ru.taf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.taf.client.RestClientYandexDictionaryClient;

@Configuration
public class ClientConfig {

    @Bean
    public RestClientYandexDictionaryClient yandexDictionaryClient(
            @Value("${yandex.dictionary.api.uri}") String yandexDictBaseUri,
            @Value("${yandex.dictionary.api.key}") String apiKey,
            @Value("${yandex.dictionary.api.lang}") String lang
    ){
        return new RestClientYandexDictionaryClient(RestClient.builder()
                .baseUrl(yandexDictBaseUri)
                .build(), apiKey, lang);
    }
}
