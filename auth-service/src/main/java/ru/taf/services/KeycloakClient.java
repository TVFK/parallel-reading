package ru.taf.services;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.taf.exceptions.KeycloakUserAlreadyExistsException;
import ru.taf.exceptions.KeycloakUserCreationException;
import ru.taf.exceptions.KeycloakUserNotFoundException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakClient implements IdentityProviderClient{

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public void createUser(String email, String password) {
        UserRepresentation user = getUserRepresentation(email, password);

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        if (!usersResource.search(email).isEmpty()) {
            throw new KeycloakUserAlreadyExistsException("User already exists: " + email);
        }

        try (var response = usersResource.create(user)) {
            if (response.getStatus() != 201) {
                throw new KeycloakUserCreationException("Failed to create user in Keycloak. Status: " + response.getStatus());
            }
        }
    }

    public void resetPassword(String email) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        List<UserRepresentation> users = usersResource.searchByEmail(email, true);
        if (users.isEmpty()) {
            throw new KeycloakUserNotFoundException();
        }

        UserRepresentation user = users.get(0);
        usersResource.get(user.getId())
                .executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    private static UserRepresentation getUserRepresentation(String email, String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(email);
        user.setEmail(email);
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        user.setRequiredActions(Collections.emptyList());
        return user;
    }
}
