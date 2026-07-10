package com.backendspring.service;

import com.backendspring.dto.EmployeePatchRequest;
import com.backendspring.dto.EmployeeRequest;
import com.backendspring.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeById(Long id);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    EmployeeResponse patchEmployee(Long id, EmployeePatchRequest request);

    void deleteEmployee(Long id);
}
