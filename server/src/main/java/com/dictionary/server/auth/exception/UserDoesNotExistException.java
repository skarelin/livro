package com.dictionary.server.auth.exception;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class UserDoesNotExistException extends AbstractCustomException {
    public UserDoesNotExistException(String message) {
        super(message, ExceptionCodes.USER_DOES_NOT_EXIST);
    }
}