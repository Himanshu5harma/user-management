package com.demo.usermanagement.repository;

import com.demo.usermanagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAll();
    UserEntity save(UserEntity user);
    Optional<UserEntity> findById(Long id);

    /**
     * Find by user name optional.
     *
     * @param userName the user name
     * @return the optional
     */
    Optional<UserEntity> findByUserName(String userName);
}
