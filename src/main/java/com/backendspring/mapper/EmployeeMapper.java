package com.backendspring.mapper;

import com.backendspring.dto.EmployeePatchRequest;
import com.backendspring.dto.EmployeeRequest;
import com.backendspring.dto.EmployeeResponse;
import com.backendspring.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequest request) {
        return Employee.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();
    }

    public EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail()
        );
    }

    public void updateEntity(Employee employee, EmployeeRequest request) {
        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setPassword(request.password());
    }

    public void patchEntity(Employee employee, EmployeePatchRequest request) {
        if (request.name() != null) {
            employee.setName(request.name());
        }
        if (request.email() != null) {
            employee.setEmail(request.email());
        }
        if (request.password() != null) {
            employee.setPassword(request.password());
        }
    }
}
