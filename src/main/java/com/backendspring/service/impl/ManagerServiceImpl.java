package com.backendspring.service.impl;

import com.backendspring.entity.Manager;
import com.backendspring.repository.ManagerRepository;
import com.backendspring.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;

    @Override
    public void createManager(Manager manager) {
        managerRepository.save(manager);
    }
}
