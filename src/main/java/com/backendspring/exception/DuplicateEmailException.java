package com.backendspring.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Student already exists with email: " + email);
    }
}
