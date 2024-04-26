package com.demo.usermanagement.utils;

import com.demo.usermanagement.model.UserEntity;
import com.demo.usermanagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * This class provides authentication for users.
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(UserAuthenticationProvider.class);

    /**
     * The UserDetailsService used to load user details.
     */
    @Autowired
    private final UserDetailsService userDetailsService;

    /**
     * The PasswordEncoder used to encode and verify passwords.
     */
    @Autowired
    private final PasswordEncoder passwordEncoder;

    /**
     * The UserRepository used to load user entities.
     */
    @Autowired
    private final UserRepository userRepository;

    /**
     * Creates a new UserAuthenticationProvider.
     *
     * @param userDetailsService the UserDetailsService used to load user details
     * @param passwordEncoder the PasswordEncoder used to encode and verify passwords
     * @param userRepository the UserRepository used to load user entities
     */
    public UserAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Authenticates a user based on the provided authentication details.
     *
     * @param authentication The authentication details.
     * @return The authenticated user.
     * @throws AuthenticationException If authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            logger.debug("Authenticating user: {}", name);
            Optional<UserEntity> result = userRepository.findByUserName(name);

            if (result.isEmpty()) {
                logger.debug("User not found: {}", name);
                throw new UsernameNotFoundException("Invalid credentials");
            }

            UserEntity user = result.get();

            if (!user.getUserName().equals(name) || !passwordEncoder.matches(password, user.getPassword())) {
                logger.debug("Invalid credentials for user: {}", name);
                throw new UsernameNotFoundException("Invalid credentials");
            }

            UserDetails principal = userDetailsService.loadUserByUsername(name);
            logger.debug("User authentication successful: {}", name);
            return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());
        } catch (UsernameNotFoundException e) {
            logger.error("User not found: {}", name, e);
            throw e;
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", name, e);
            throw new AuthenticationServiceException("Authentication failed", e);
        }
    }

    /**
     * This method checks if the provided authentication class is supported.
     *
     * @param authentication The authentication class to check.
     * @return true if the authentication class is supported, false otherwise.
     * @throws IllegalArgumentException if the authentication object is not of the expected type.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        try {
            logger.debug("Checking if authentication is supported.");
            return authentication.equals(UsernamePasswordAuthenticationToken.class);
        } catch (IllegalArgumentException e) {
            logger.error("Unsupported authentication object: {}", authentication, e);
            throw e;
        }
    }
}
