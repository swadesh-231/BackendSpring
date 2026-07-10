package com.backendspring.repository;

import com.backendspring.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByIdAndDeletedIsFalse(Long id);
    List<Employee> findByDeletedIsFalse();
    boolean existsByEmailAndDeletedIsFalse(String email);
    boolean existsByEmailAndDeletedIsFalseAndIdNot(String email, Long id);
}
