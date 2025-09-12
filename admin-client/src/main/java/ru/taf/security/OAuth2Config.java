package ru.taf.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class OAuth2Config {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties properties) {
        OAuth2ClientProperties.Registration reg = properties.getRegistration().get("keycloak");
        OAuth2ClientProperties.Provider prov = properties.getProvider().get("keycloak");

        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId("keycloak")
                .clientId(reg.getClientId())
                .clientSecret(reg.getClientSecret())
                .authorizationGrantType(new AuthorizationGrantType(reg.getAuthorizationGrantType()))
                .redirectUri(reg.getRedirectUri())
                .scope(reg.getScope())
                .userNameAttributeName(prov.getUserNameAttribute());

        String issuerUri = prov.getIssuerUri();
        if (issuerUri != null && !issuerUri.isEmpty()) {
            builder.issuerUri(issuerUri);
        } else {
            builder.authorizationUri(prov.getAuthorizationUri())
                    .tokenUri(prov.getTokenUri())
                    .userInfoUri(prov.getUserInfoUri())
                    .jwkSetUri(prov.getJwkSetUri());
        }

        ClientRegistration cr = builder.build();
        return new InMemoryClientRegistrationRepository(cr);
    }
}