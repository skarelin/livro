package com.dictionary.server.auth.exception;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class UserSessionIsNotActive extends AbstractCustomException {
    public UserSessionIsNotActive(String message) {
        super(message, ExceptionCodes.USER_SESSION_IS_NOT_ACTIVE);
    }
}