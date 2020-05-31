package com.dictionary.server.exception;

public abstract class AbstractCustomException extends RuntimeException {
    private final int code;
    public AbstractCustomException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
