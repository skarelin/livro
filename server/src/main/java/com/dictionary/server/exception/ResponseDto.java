package com.dictionary.server.exception;

import lombok.Data;

@Data
public class ResponseDto {
    private final String message;
    private final Boolean error;
    private final int errorCode;
    private final int httpCode;
}