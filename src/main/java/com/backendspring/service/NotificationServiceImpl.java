package com.backendspring.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class NotificationServiceImpl implements NotificationService {
    @Override
    public String send() {
        return "Send message from notification service";
    }
}
