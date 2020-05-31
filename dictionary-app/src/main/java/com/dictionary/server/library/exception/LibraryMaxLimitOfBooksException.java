package com.dictionary.server.library.exception;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class LibraryMaxLimitOfBooksException extends AbstractCustomException {
    public LibraryMaxLimitOfBooksException(String message) {
        super(message, ExceptionCodes.MAX_LIMIT_OF_BOOKS_FOR_USER);
    }
}