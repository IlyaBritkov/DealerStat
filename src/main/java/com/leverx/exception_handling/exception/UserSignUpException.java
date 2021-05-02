package com.leverx.exception_handling.exception;

public class UserSignUpException extends RuntimeException {
    public UserSignUpException(String message) {
        super(message);
    }
}
