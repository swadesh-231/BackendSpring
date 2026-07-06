package com.backendspring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record StudentPatchRequest(
        @Size(max = 100, message = "name must be at most 100 characters")
        String name,
        @Email(message = "email must be a valid email address")
        String email,
        @Size(max = 100, message = "course must be at most 100 characters")
        String course,
        @Min(value = 1, message = "age must be at least 1")
        @Max(value = 120, message = "age must be at most 120")
        Integer age
) {
}
