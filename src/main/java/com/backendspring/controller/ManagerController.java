package com.backendspring.controller;

import com.backendspring.dto.ManagerPatchRequest;
import com.backendspring.dto.ManagerRequest;
import com.backendspring.dto.ManagerResponse;
import com.backendspring.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping
    public ResponseEntity<ManagerResponse> createManager(@Valid @RequestBody ManagerRequest request) {
        return ResponseEntity.ok(managerService.createManager(request));
    }

    @GetMapping
    public ResponseEntity<List<ManagerResponse>> getAllManagers() {
        return ResponseEntity.ok(managerService.getAllManagers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerResponse> getManagerById(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.getManagerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagerResponse> updateManager(@PathVariable Long id, @Valid @RequestBody ManagerRequest request) {
        return ResponseEntity.ok(managerService.updateManager(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ManagerResponse> patchManager(@PathVariable Long id, @Valid @RequestBody ManagerPatchRequest request) {
        return ResponseEntity.ok(managerService.patchManager(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }
}
