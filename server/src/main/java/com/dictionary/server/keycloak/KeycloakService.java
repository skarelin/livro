package com.dictionary.server.keycloak;

import com.dictionary.server.auth.model.LoginUserDTO;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

interface KeycloakService {
    CredentialRepresentation getCredentialRepresentation(String password);
    UserRepresentation getUserRepresentation(String username, CredentialRepresentation credential);

    String registerUser(UserRepresentation userRepresentation);
    String loginUser(LoginUserDTO user);

    void logoutAllUsersAfterRestart();
}
