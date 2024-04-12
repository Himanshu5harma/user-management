package com.demo.usermanagement.utils;

import com.demo.usermanagement.model.UserEntity;
import com.demo.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
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
 * The type User authentication provider.
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private final UserDetailsService userDetailsService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository userRepository;

    /**
     * Instantiates a new User authentication provider.
     *
     * @param userDetailsService the user details service
     * @param passwordEncoder    the password encoder
     * @param userRepository     the user repository
     */
    public UserAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<UserEntity> result = userRepository.findByUserName(name);
        result.orElseThrow(()-> new UsernameNotFoundException("Invalid credentials"));
        UserEntity user = result.get();
        if (user.getUserName().equals(name) && passwordEncoder.matches(password,user.getPassword())) {
            UserDetails principal = userDetailsService.loadUserByUsername(name);
            return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
