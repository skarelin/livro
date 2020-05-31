package com.dictionary.server.exception;

public interface ExceptionCodes {
    int BAD_USER_CREDENTIALS = 1;
    int USER_DOES_NOT_EXIST = 2;
    int USER_SESSION_IS_NOT_ACTIVE = 3;

    // Library: 101-200
    int BOOK_DOES_NOT_EXIST = 101;
    int WORD_IS_NOT_FOUND_IN_EXTERNAL_REPO = 102;
    int UNEXPECTED_PROBLEM_WITH_EXTERNAL_REPO = 103;
    int DICTIONARY_PARSE_ERROR = 104;
    int WORD_IS_NOT_CORRECT = 105;
    int USER_DICTIONARY_LIMIT = 106;
    int LIBRARY_EXCEPTION = 107;
    int EXTERNAL_LIBRARY_EXCEPTION = 108;

    // File manager: 201-300
    int FILE_EXTENSION_ERROR = 201;
    int FILE_MANAGER_EXCEPTION = 202;
    int MAX_LIMIT_OF_BOOKS_FOR_USER = 203;

    // Keycloak: 301-350
    int GLOBAL_KEYCLOAK_EXCEPTION = 301;
}
