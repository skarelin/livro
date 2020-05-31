package com.dictionary.server.library.exception;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class BookIsNotExistException extends AbstractCustomException {
    public BookIsNotExistException(String message) {
        super(message, ExceptionCodes.BOOK_DOES_NOT_EXIST);
    }
}