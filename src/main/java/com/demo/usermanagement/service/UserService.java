package com.demo.usermanagement.service;

import com.demo.usermanagement.model.UserEntity;

import java.util.List;

/**
 * The UserService interface provides methods to interact with the user data.
 */
public interface UserService {

    /**
     * Gets all users.
     *
     * @return the list of all users
     */
    public List<UserEntity> getAllUsers();

    /**
     * Gets a user by their ID.
     *
     * @param id the ID of the user
     * @return the user with the specified ID
     */
    public UserEntity getUserById(Long id);

    /**
     * Gets a user by their username.
     *
     * @param userName the username of the user
     * @return the user with the specified username
     */
    public UserEntity getUserByUserName(String userName);

    /**
     * Creates a new user.
     *
     * @param user the user object to be created
     * @return the created user object
     */
    public UserEntity createUser(UserEntity user);
}
