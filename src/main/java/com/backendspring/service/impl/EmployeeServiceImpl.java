package com.backendspring.service.impl;

import com.backendspring.dto.EmployeePatchRequest;
import com.backendspring.dto.EmployeeRequest;
import com.backendspring.dto.EmployeeResponse;
import com.backendspring.entity.Employee;
import com.backendspring.exception.DuplicateEmailException;
import com.backendspring.exception.EmployeeNotFoundException;
import com.backendspring.mapper.EmployeeMapper;
import com.backendspring.repository.EmployeeRepository;
import com.backendspring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByEmailAndDeletedIsFalse(request.email())) {
            throw new DuplicateEmailException(request.email());
        }
        Employee employee = employeeMapper.toEntity(request);
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findByDeletedIsFalse().stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        return employeeMapper.toResponse(findEmployee(id));
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = findEmployee(id);
        verifyEmailAvailable(request.email(), id);
        employeeMapper.updateEntity(employee, request);
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Override
    public EmployeeResponse patchEmployee(Long id, EmployeePatchRequest request) {
        Employee employee = findEmployee(id);
        verifyEmailAvailable(request.email(), id);
        employeeMapper.patchEntity(employee, request);
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = findEmployee(id);
        employee.setDeleted(true);
        employeeRepository.save(employee);
    }

    private Employee findEmployee(Long id) {
        return employeeRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    private void verifyEmailAvailable(String email, Long id) {
        if (email != null && employeeRepository.existsByEmailAndDeletedIsFalseAndIdNot(email, id)) {
            throw new DuplicateEmailException(email);
        }
    }
}
