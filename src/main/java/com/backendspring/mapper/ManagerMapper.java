package com.backendspring.mapper;

import com.backendspring.dto.ManagerPatchRequest;
import com.backendspring.dto.ManagerRequest;
import com.backendspring.dto.ManagerResponse;
import com.backendspring.entity.Manager;
import org.springframework.stereotype.Component;

@Component
public class ManagerMapper {

    public Manager toEntity(ManagerRequest request) {
        return Manager.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();
    }

    public ManagerResponse toResponse(Manager manager) {
        return new ManagerResponse(
                manager.getId(),
                manager.getName(),
                manager.getEmail()
        );
    }

    public void updateEntity(Manager manager, ManagerRequest request) {
        manager.setName(request.name());
        manager.setEmail(request.email());
        manager.setPassword(request.password());
    }

    public void patchEntity(Manager manager, ManagerPatchRequest request) {
        if (request.name() != null) {
            manager.setName(request.name());
        }
        if (request.email() != null) {
            manager.setEmail(request.email());
        }
        if (request.password() != null) {
            manager.setPassword(request.password());
        }
    }
}
