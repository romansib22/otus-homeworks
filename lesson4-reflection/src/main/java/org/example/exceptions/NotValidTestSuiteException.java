package org.example.exceptions;

public class NotValidTestSuiteException extends RuntimeException {
    public NotValidTestSuiteException(String message) {
        super(message);
    }
}
