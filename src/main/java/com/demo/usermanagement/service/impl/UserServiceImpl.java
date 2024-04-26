package com.demo.usermanagement.service.impl;

import com.demo.usermanagement.model.UserEntity;
import com.demo.usermanagement.repository.UserRepository;
import com.demo.usermanagement.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * The Encoder.
     */
    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves all users from the user repository and returns them as a list.
     *
     * @return a list of UserEntity objects representing all users in the system
     * @throws RuntimeException if there is an error retrieving the users
     */
    @Override
    public List<UserEntity> getAllUsers() {
        try {
            logger.debug("Retrieving all users...");
            List<UserEntity> allUsers = userRepository.findAll();
            logger.debug("Retrieved {} users", allUsers.size());
            return allUsers;
        } catch (Exception e) {
            logger.error("Error retrieving all users: {}", e.getMessage());
            throw new RuntimeException("Error retrieving all users", e);
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the user with the specified ID
     * @throws EntityNotFoundException if the user is not found
     */
    @Override
    public UserEntity getUserById(Long id) {
        try {
            logger.debug("Retrieving user with ID: {}", id);
            UserEntity user = userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("User Not Found"));
            logger.debug("Retrieved user with ID: {}", id);
            return user;
        } catch (Exception e) {
            logger.error("Error retrieving user with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error retrieving user", e);
        }
    }

    /**
     * Retrieves a user by their username.
     *
     * @param userName the username of the user to retrieve
     * @return the UserEntity object representing the user
     * @throws EntityNotFoundException if the user is not found
     */
    @Override
    public UserEntity getUserByUserName(String userName) {
        try {
            logger.debug("Fetching user by username: {}", userName);
            Optional<UserEntity> user = userRepository.findByUserName(userName);
            if (user.isPresent()) {
                logger.debug("User found: {}", user.get());
                return user.get();
            } else {
                logger.error("User not found: {}", userName);
                throw new EntityNotFoundException("User Not Found");
            }
        } catch (Exception e) {
            logger.error("Error fetching user by username: {}", userName, e);
            throw new RuntimeException("Error fetching user by username", e);
        }
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        try {
            if(user.getId() == null) {
                logger.debug("Checking if user name is already taken...");
                Optional<UserEntity> userEntity = userRepository.findByUserName(user.getUserName());
                if(userEntity.isPresent()){
                    logger.error("User Name already Taken.");
                    throw new RuntimeException("User Name already Taken.");
                }
                logger.debug("Encoding password...");
                user.setPassword(encoder.encode(user.getPassword()));
            } else {
                logger.debug("Updating existing user...");
            }
            logger.debug("Saving user to repository...");
            UserEntity savedUser = userRepository.save(user);
            logger.debug("User saved successfully.");
            return savedUser;
        } catch (Exception e) {
            logger.error("Error while creating or updating user: ", e);
            throw e;
        }
    }
}
