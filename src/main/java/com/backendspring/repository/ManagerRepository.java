package com.backendspring.repository;

import com.backendspring.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByIdAndDeletedIsFalse(Long id);
    List<Manager> findByDeletedIsFalse();
    boolean existsByEmailAndDeletedIsFalse(String email);
    boolean existsByEmailAndDeletedIsFalseAndIdNot(String email, Long id);
}
