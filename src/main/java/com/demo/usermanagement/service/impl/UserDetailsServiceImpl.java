package com.demo.usermanagement.service.impl;

import com.demo.usermanagement.model.UserEntity;
import com.demo.usermanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * The type User details service.
 */
@Component
@Qualifier
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * The User repository.
     */
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUserName(username);
        user.orElseThrow(()-> new EntityNotFoundException("User Not Found"));

        UserEntity currentUser = user.get();

        UserDetails userDetails = User.builder().username(currentUser.getUserName())
                .password(currentUser.getPassword())
                .roles(currentUser.getRoles().stream().map(role->role.getName()).toList().toArray(new String[0]))
                .build();
        System.out.println(userDetails.getPassword());
        return userDetails;
    }
}
