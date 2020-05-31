package com.dictionary.server.library.exception;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class ExternalRepoException extends AbstractCustomException {
    public ExternalRepoException(String message) {
        super(message, ExceptionCodes.UNEXPECTED_PROBLEM_WITH_EXTERNAL_REPO);
    }
}