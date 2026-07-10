package com.backendspring.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("A record already exists with email: " + email);
    }
}
