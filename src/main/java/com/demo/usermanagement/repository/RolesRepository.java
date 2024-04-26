package com.demo.usermanagement.repository;

import com.demo.usermanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * It represents a repository for managing Role entities in the database.
 */
public interface RolesRepository extends JpaRepository<Role, Long> {

    /**
     * Retrieves all Role entities from the database.
     *
     * @return a list of Role entities.
     */
    List<Role> findAll();

    /**
     * Retrieves a Role entity by its name.
     *
     * @param name the name of the Role entity to find.
     * @return an Optional containing the Role entity if found, or an empty Optional if not found.
     */
    Optional<Role> findByName(String name);

    /**
     * Saves a Role entity to the database.
     *
     * @param role the Role entity to save.
     * @return the saved Role entity.
     */
    Role save(Role role);

    /**
     * Deletes a Role entity from the database by its ID.
     *
     * @param id the ID of the Role entity to delete.
     */
    void deleteById(Long id);

    /**
     * Retrieves a Role entity by its ID.
     *
     * @param id the ID of the Role entity to find.
     * @return an Optional containing the Role entity if found, or an empty Optional if not found.
     */
    Optional<Role> findById(Long id);
}
