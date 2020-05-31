package com.dictionary.server.keycloak;

import com.dictionary.server.auth.model.LoginUserDTO;
import com.dictionary.server.auth.model.RegisterUserDTO;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeycloakFacade {

    private KeycloakService keycloakService;

    public static final String TEST_USERNAME = "TEST_USERNAME";
    private static final String TEST_PASSWORD = "TEST_USERNAME";

    @Autowired
    KeycloakFacade(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    public String registerUser(RegisterUserDTO user) {
        CredentialRepresentation credential = keycloakService.getCredentialRepresentation(user.getPassword());
        UserRepresentation userRepresentation = keycloakService.getUserRepresentation(user.getUsername(), credential);

        return keycloakService.registerUser(userRepresentation); // uuid
    }

    public String loginUser(LoginUserDTO user) {
        return keycloakService.loginUser(user);
    }

    public String testConnection() {
        LoginUserDTO testUser = new LoginUserDTO();
        testUser.setUsername(TEST_USERNAME);
        testUser.setPassword(TEST_PASSWORD);

        return keycloakService.loginUser(testUser);
    }
}
