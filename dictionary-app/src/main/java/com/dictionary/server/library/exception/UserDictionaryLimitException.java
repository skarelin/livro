package com.dictionary.server.library.exception;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class UserDictionaryLimitException extends AbstractCustomException {
    public UserDictionaryLimitException(String message) {
        super(message, ExceptionCodes.USER_DICTIONARY_LIMIT);
    }
}