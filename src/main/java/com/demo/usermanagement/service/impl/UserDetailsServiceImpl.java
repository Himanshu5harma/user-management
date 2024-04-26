package com.demo.usermanagement.service.impl;

import com.demo.usermanagement.model.Role;
import com.demo.usermanagement.model.UserEntity;
import com.demo.usermanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * The UserDetailsServiceImpl class implements the UserDetailsService interface.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    /**
     * The User repository.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Loads user details by username.
     *
     * @param username the username of the user to load
     * @return the UserDetails object for the specified username
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            logger.info("Loading user details for username: {}", username);

            Optional<UserEntity> user = userRepository.findByUserName(username);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User Not Found");
            }

            UserEntity currentUser = user.get();

            UserDetails userDetails = User.builder()
                    .username(currentUser.getUserName())
                    .password(currentUser.getPassword())
                    .roles(currentUser.getRoles().stream().map(Role::getName).toArray(String[]::new))
                    .build();

            logger.info("User details loaded successfully for username: {}", username);
            return userDetails;
        } catch (Exception e) {
            logger.error("Error loading user details for username: {}. Error: {}", username, e.getMessage());
            throw new UsernameNotFoundException("Error loading user details");
        }
    }
}
