package com.backendspring.exception;

public class ManagerNotFoundException extends RuntimeException {

    public ManagerNotFoundException(Long id) {
        super("Manager not found with id: " + id);
    }
}
