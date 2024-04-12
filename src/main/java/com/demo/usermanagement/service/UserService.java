package com.demo.usermanagement.service;

import com.demo.usermanagement.model.UserEntity;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Gets all users.
     *
     * @return the all users
     */
    public List<UserEntity> getAllUsers();

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    public UserEntity getUserById(Long id);

    /**
     * Gets user by user name.
     *
     * @param userName the user name
     * @return the user by user name
     */
    public UserEntity getUserByUserName(String userName);

    /**
     * Create user user entity.
     *
     * @param user the user
     * @return the user entity
     */
    public UserEntity createUser(UserEntity user);
}
