package com.backendspring.dto;

public record EmployeeResponse(
        Long id,
        String name,
        String email
) {
}
