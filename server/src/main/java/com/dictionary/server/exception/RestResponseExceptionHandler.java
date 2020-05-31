package com.dictionary.server.exception;

import com.dictionary.server.auth.exception.*;
import com.dictionary.server.keycloak.KeycloakException;
import com.dictionary.server.library.exception.*;
import com.dictionary.server.manager.file.FileExtensionException;
import com.dictionary.server.manager.file.FileManagerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserBadCredentialsException.class})
    public ResponseEntity<Object> handleAuthenticationException(AbstractCustomException ex, WebRequest request) {
        return handleException(ex, request, UNAUTHORIZED);
    }

    @ExceptionHandler({
            UserDictionaryLimitException.class,
            UserSessionIsNotActive.class
    })
    public ResponseEntity<Object> handleForbiddenException(AbstractCustomException ex, WebRequest request) {
        return handleException(ex, request, FORBIDDEN);
    }

    @ExceptionHandler({
            UserAlreadyExistException.class,
            ExternalRepoException.class,
            DictionaryParseException.class,
            WordIsNotCorrectException.class,
            LibraryException.class,
            FileExtensionException.class,
            FileManagerException.class,
            UserException.class,
            ExternalLibraryException.class,
            KeycloakException.class})
    public ResponseEntity<Object> handleBadRequestException(AbstractCustomException ex, WebRequest request) {
        return handleException(ex, request, BAD_REQUEST);
    }

    @ExceptionHandler({
            BookIsNotExistException.class,
            WordIsNotFoundInExternalRepoException.class,
            UserDoesNotExistException.class})
    public ResponseEntity<Object> handleNotFoundException(AbstractCustomException ex, WebRequest request) {
        return handleException(ex, request, NOT_FOUND);
    }

    @ExceptionHandler({
            LibraryMaxLimitOfBooksException.class})
    public ResponseEntity<Object> handleMethodNotAllowedException(AbstractCustomException ex, WebRequest request) {
        return handleException(ex, request, METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<Object> handleException(AbstractCustomException ex, WebRequest request, HttpStatus status) {
        log.error("Handled exception: " + ex.getMessage());
        return handleExceptionInternal(ex, getResponseBody(ex, status),
                new HttpHeaders(), status, request);
    }


    private ResponseDto getResponseBody(AbstractCustomException ex, HttpStatus httpStatus) {
        return new ResponseDto(ex.getMessage(), true, ex.getCode(), httpStatus.value());
    }
}