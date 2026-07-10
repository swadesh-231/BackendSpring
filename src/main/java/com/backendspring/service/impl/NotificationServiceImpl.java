package com.backendspring.service.impl;

import com.backendspring.service.NotificationService;
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
