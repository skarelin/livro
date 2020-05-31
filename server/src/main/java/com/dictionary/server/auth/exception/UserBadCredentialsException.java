package com.dictionary.server.auth.exception;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class UserBadCredentialsException extends AbstractCustomException {
    public UserBadCredentialsException(String message) {
        super(message, ExceptionCodes.BAD_USER_CREDENTIALS);
    }
}