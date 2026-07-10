package com.backendspring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record EmployeePatchRequest(
        @Size(max = 100, message = "name must be at most 100 characters")
        String name,
        @Email(message = "email must be a valid email address")
        String email,
        @Size(min = 8, max = 100, message = "password must be between 8 and 100 characters")
        String password
) {
}
