package com.demo.usermanagement.repository;

import com.demo.usermanagement.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Permission repository.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAll();
    Optional<Permission> findByName(String name);
    Permission save(Permission permission);
    void deleteById(Long id);
    Optional<Permission> findById(Long id);
}
