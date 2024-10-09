package ru.taf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClient;
import ru.taf.client.BooksRestClient;
import ru.taf.client.BooksRestClientImpl;

@Configuration
public class ClientConfig {

    @Bean
    @Scope("prototype")
    public BooksRestClientImpl serviceRestClientBuilder(
            @Value("${services.books.uri:http://localhost:8080}") String booksServiceUri
    ){
        return new BooksRestClientImpl(RestClient.builder()
                .baseUrl(booksServiceUri)
                .build());
    }
}
