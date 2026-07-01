package com.backendspring.dto;

public record StudentResponse(
        Long id,
        String name,
        String email,
        String course,
        Integer age
) {
}
