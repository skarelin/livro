package com.dictionary.server.keycloak;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
class KeycloakProperties {

    static boolean USER_LOGOUT_BLOCKADE = false;

    @Value("${app.keycloak.grant-type}")
    private String grantType;

    @Value("${app.keycloak.token-url}")
    private String tokenUrl;

    @Value("${app.keycloak.register-url}")
    private String registerUrl;

    @Value("${app.keycloak.realm}")
    private String realm;

    @Value("${app.keycloak.adm-username}")
    private String admUsername;

    @Value("${app.keycloak.adm-pass}")
    private String admPass;

    @Value("${app.keycloak.client-id}")
    private String clientId;

}