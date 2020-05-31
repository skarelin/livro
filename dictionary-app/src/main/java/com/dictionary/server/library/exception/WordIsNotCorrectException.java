package com.dictionary.server.library.exception;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class WordIsNotCorrectException extends AbstractCustomException {
    public WordIsNotCorrectException(String message) {
        super(message, ExceptionCodes.WORD_IS_NOT_CORRECT);
    }
}