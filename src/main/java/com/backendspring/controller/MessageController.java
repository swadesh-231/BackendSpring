package com.backendspring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Value("${app.message}")
    private String message;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @GetMapping("/message")
    public String getMessage() {
        return message;
    }

    @GetMapping("/profile")
    public String getActiveProfile() {
        return activeProfile;
    }
}
