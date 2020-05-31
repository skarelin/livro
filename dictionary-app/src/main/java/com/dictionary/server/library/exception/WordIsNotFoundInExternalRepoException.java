package com.dictionary.server.library.exception;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class WordIsNotFoundInExternalRepoException extends AbstractCustomException {
    public WordIsNotFoundInExternalRepoException(String message) {
        super(message, ExceptionCodes.WORD_IS_NOT_FOUND_IN_EXTERNAL_REPO);
    }
}