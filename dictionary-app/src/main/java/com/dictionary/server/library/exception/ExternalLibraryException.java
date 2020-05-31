package com.dictionary.server.library.exception;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class ExternalLibraryException extends AbstractCustomException {
    public ExternalLibraryException(String message) {
        super(message, ExceptionCodes.EXTERNAL_LIBRARY_EXCEPTION);
    }
}