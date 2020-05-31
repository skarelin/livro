package com.dictionary.server.manager.file;

import com.dictionary.server.exception.AbstractCustomException;
import com.dictionary.server.exception.ExceptionCodes;

public class FileManagerException extends AbstractCustomException {
    public FileManagerException(String message) {
        super(message, ExceptionCodes.FILE_MANAGER_EXCEPTION);
    }
}