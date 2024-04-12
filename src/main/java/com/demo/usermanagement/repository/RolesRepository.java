package com.demo.usermanagement.repository;

import com.demo.usermanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Roles repository.
 */
@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();
    Optional<Role> findByName(String name);
    Role save(Role role);
    void deleteById(Long id);
    Optional<Role> findById(Long id);
}
