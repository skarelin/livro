package com.dictionary.server.keycloak;

import com.dictionary.server.auth.exception.UserBadCredentialsException;
import com.dictionary.server.auth.model.LoginUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

@Service
@Slf4j
class KeycloakServiceImpl implements KeycloakService {

    private KeycloakProperties keycloakProperties;

    @Autowired
    KeycloakServiceImpl(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    @Override
    public CredentialRepresentation getCredentialRepresentation(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        return credential;
    }

    @Override
    public UserRepresentation getUserRepresentation(String username, CredentialRepresentation credential) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(username);
        userRepresentation.setCredentials(Collections.singletonList(credential));
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    @Override
    public String registerUser(UserRepresentation userRepresentation) {
        Response response = getClient().realm(keycloakProperties.getRealm()).users().create(userRepresentation);
        return getUserUuid(response);
    }


    @Override
    public String loginUser(LoginUserDTO user) {

        ResponseEntity<Map> keycloakResponse = loginKeycloakUser(user.getUsername(), user.getPassword());

        String token;
        if (keycloakResponse.getBody() == null) {
            throw new KeycloakException("Keycloak response body is null!");
        }

        if (keycloakResponse.getBody().get("access_token") != null) {
            token = keycloakResponse.getBody().get("access_token").toString();
            log.info("User " + user.getUsername() + " successfully authorized");
            return token;
        } else {
            throw new KeycloakException("Keycloak didn't return auth token!");
        }
    }

    @Override
    @Deprecated
    public void logoutAllUsersAfterRestart() {
        if(KeycloakProperties.USER_LOGOUT_BLOCKADE) {
            throw new KeycloakException("Cannot logout all users! Blockade is enabled!");
        } else {
            // logoutAllUsers();
            KeycloakProperties.USER_LOGOUT_BLOCKADE = true;
        }
    }

//    private void logoutAllUsers() {
//        log.info("[Keycloak]: Logout all active users!");
//        getClient().realm(keycloakProperties.getRealm()).clients()
//    }

    private ResponseEntity<Map> loginKeycloakUser(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> keycloakParams =
                new LinkedMultiValueMap<String, String>();
        keycloakParams.add("grant_type", keycloakProperties.getGrantType());
        keycloakParams.add("client_id", keycloakProperties.getClientId());
        keycloakParams.add("username", username);
        keycloakParams.add("password", password);

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<MultiValueMap<String, String>>(keycloakParams, headers);

        ResponseEntity<Map> keycloakResponse;
        try {
            keycloakResponse =
                    restTemplate.exchange(keycloakProperties.getTokenUrl(),
                            HttpMethod.POST,
                            entity,
                            Map.class);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED || exception.getStatusCode() != HttpStatus.OK) {
                throw new UserBadCredentialsException("Bad user library's credentials");
            } else {
                throw new KeycloakException("Error while tried to login user library by keycloak: " + exception);
            }
        }
        return keycloakResponse;
    }

    private Keycloak getClient() {
        return Keycloak.getInstance(
                keycloakProperties.getRegisterUrl(),
                keycloakProperties.getRealm(),
                keycloakProperties.getAdmUsername(), keycloakProperties.getAdmPass(),
                keycloakProperties.getClientId());
    }

    private String getUserUuid(Response response) {
        URI location = response.getLocation();
        if (!response.getStatusInfo().equals(Response.Status.CREATED) || location == null) {
            Response.StatusType statusInfo = response.getStatusInfo();
            log.error("Cannot get user library id from keycloak, status info: " + statusInfo);
            return null;
        }
        String path = location.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
