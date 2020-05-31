package com.dictionary.server.keycloak;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class KeycloakException extends AbstractCustomException {
    public KeycloakException(String message) {
        super(message, ExceptionCodes.GLOBAL_KEYCLOAK_EXCEPTION);
    }
}