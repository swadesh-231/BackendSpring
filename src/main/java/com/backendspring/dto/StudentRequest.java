package com.backendspring.dto;

public record StudentRequest(
        String name,
        String email,
        String course,
        Integer age
) {
}
