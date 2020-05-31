package com.dictionary.server.library.exception;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class DictionaryParseException extends AbstractCustomException {
    public DictionaryParseException(String message) {
        super(message, ExceptionCodes.DICTIONARY_PARSE_ERROR);
    }
}