package com.dictionary.server.dictionary.oxford;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class OxfordApiException extends AbstractCustomException {
    public OxfordApiException(String message) {
        super(message, ExceptionCodes.LIBRARY_EXCEPTION);
    }
}

