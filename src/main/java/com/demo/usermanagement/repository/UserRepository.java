package com.demo.usermanagement.repository;

import com.demo.usermanagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface extends the JpaRepository interface to manage UserEntity objects in the database.
 * It provides methods for basic CRUD operations (Create, Read, Update, Delete) and custom operations.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds all UserEntity objects in the database.
     *
     * @return a list of UserEntity objects
     */
    List<UserEntity> findAll();

    /**
     * Saves a UserEntity object to the database.
     *
     * @param user the UserEntity object to save
     * @return the saved UserEntity object
     */
    UserEntity save(UserEntity user);

    /**
     * Finds a UserEntity object by its ID.
     *
     * @param id the ID of the UserEntity object to find
     * @return an Optional containing the UserEntity object if found, or an empty Optional if not found
     */
    Optional<UserEntity> findById(Long id);

    /**
     * Finds a UserEntity object by its user name.
     *
     * @param userName the user name to search for
     * @return an Optional containing the UserEntity object if found, or an empty Optional if not found
     */
    Optional<UserEntity> findByUserName(String userName);
}
