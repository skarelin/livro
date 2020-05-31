package com.dictionary.server.manager.file;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class FileExtensionException extends AbstractCustomException {
    public FileExtensionException(String message) {
        super(message, ExceptionCodes.FILE_EXTENSION_ERROR);
    }
}