package com.backendspring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse(
        String message,
        boolean status,
        LocalDateTime timestamp,
        Map<String, String> errors
) {
    public static ApiResponse of(String message, boolean status) {
        return new ApiResponse(message, status, LocalDateTime.now(), null);
    }

    public static ApiResponse of(String message, boolean status, Map<String, String> errors) {
        return new ApiResponse(message, status, LocalDateTime.now(), errors);
    }
}
