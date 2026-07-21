package com.backendspring.service.impl;

import com.backendspring.aop.LogExecutionTime;
import com.backendspring.dto.ManagerPatchRequest;
import com.backendspring.dto.ManagerRequest;
import com.backendspring.dto.ManagerResponse;
import com.backendspring.entity.Manager;
import com.backendspring.exception.DuplicateEmailException;
import com.backendspring.exception.ManagerNotFoundException;
import com.backendspring.mapper.ManagerMapper;
import com.backendspring.repository.ManagerRepository;
import com.backendspring.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;

    @Override
    public ManagerResponse createManager(ManagerRequest request) {
        if (managerRepository.existsByEmailAndDeletedIsFalse(request.email())) {
            throw new DuplicateEmailException(request.email());
        }
        Manager manager = managerMapper.toEntity(request);
        return managerMapper.toResponse(managerRepository.save(manager));
    }

    @Override
    @LogExecutionTime
    public List<ManagerResponse> getAllManagers() {
        return managerRepository.findByDeletedIsFalse().stream()
                .map(managerMapper::toResponse)
                .toList();
    }

    @Override
    public ManagerResponse getManagerById(Long id) {
        return managerMapper.toResponse(findManager(id));
    }

    @Override
    public ManagerResponse updateManager(Long id, ManagerRequest request) {
        Manager manager = findManager(id);
        verifyEmailAvailable(request.email(), id);
        managerMapper.updateEntity(manager, request);
        return managerMapper.toResponse(managerRepository.save(manager));
    }

    @Override
    public ManagerResponse patchManager(Long id, ManagerPatchRequest request) {
        Manager manager = findManager(id);
        verifyEmailAvailable(request.email(), id);
        managerMapper.patchEntity(manager, request);
        return managerMapper.toResponse(managerRepository.save(manager));
    }

    @Override
    public void deleteManager(Long id) {
        Manager manager = findManager(id);
        manager.setDeleted(true);
        managerRepository.save(manager);
    }

    private Manager findManager(Long id) {
        return managerRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ManagerNotFoundException(id));
    }

    private void verifyEmailAvailable(String email, Long id) {
        if (email != null && managerRepository.existsByEmailAndDeletedIsFalseAndIdNot(email, id)) {
            throw new DuplicateEmailException(email);
        }
    }
}
