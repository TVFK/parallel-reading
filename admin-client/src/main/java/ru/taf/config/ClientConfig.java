package ru.taf.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.observation.DefaultClientRequestObservationConvention;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.taf.client.BooksRestClientImpl;
import ru.taf.security.OAuthClientRequestInterceptor;

@Configuration
public class ClientConfig {

    @Bean
    @Scope("prototype")
    public BooksRestClientImpl serviceRestClientBuilder(
            @Value("${services.books.uri:http://localhost:8080}") String booksServiceUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${services.books.registration-id:keycloak}") String registrationId,
            ObservationRegistry observationRegistry
            ) {
        return new BooksRestClientImpl(RestClient.builder()
                .baseUrl(booksServiceUri)
                .requestInterceptor(
                        new OAuthClientRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository,
                                        authorizedClientRepository
                                ), registrationId
                        )
                )
                .observationRegistry(observationRegistry)
                .observationConvention(new DefaultClientRequestObservationConvention())
                .build());
    }
}
