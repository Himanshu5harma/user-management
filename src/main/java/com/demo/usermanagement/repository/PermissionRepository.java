package com.demo.usermanagement.repository;

import com.demo.usermanagement.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for managing Permission entities in the database.
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * Retrieves all Permission entities from the database.
     *
     * @return a list of Permission entities.
     */
    List<Permission> findAll();

    /**
     * Retrieves a Permission entity by its name.
     *
     * @param name the name of the Permission entity to find.
     * @return an Optional containing the Permission entity if found, or an empty Optional if not found.
     */
    Optional<Permission> findByName(String name);

    /**
     * Saves a Permission entity to the database.
     *
     * @param permission the Permission entity to save.
     * @return the saved Permission entity.
     */
    Permission save(Permission permission);

    /**
     * Deletes a Permission entity from the database by its ID.
     *
     * @param id the ID of the Permission entity to delete.
     */
    void deleteById(Long id);

    /**
     * Retrieves a Permission entity by its ID.
     *
     * @param id the ID of the Permission entity to find.
     * @return an Optional containing the Permission entity if found, or an empty Optional if not found.
     */
    Optional<Permission> findById(Long id);
}
