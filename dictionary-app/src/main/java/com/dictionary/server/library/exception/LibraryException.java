package com.dictionary.server.library.exception;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class LibraryException extends AbstractCustomException {
    public LibraryException(String message) {
        super(message, ExceptionCodes.LIBRARY_EXCEPTION);
    }
}