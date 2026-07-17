package com.backendspring.service;

import com.backendspring.dto.ManagerPatchRequest;
import com.backendspring.dto.ManagerRequest;
import com.backendspring.dto.ManagerResponse;

import java.util.List;

public interface ManagerService {

    ManagerResponse createManager(ManagerRequest request);

    List<ManagerResponse> getAllManagers();

    ManagerResponse getManagerById(Long id);

    ManagerResponse updateManager(Long id, ManagerRequest request);

    ManagerResponse patchManager(Long id, ManagerPatchRequest request);

    void deleteManager(Long id);
}
