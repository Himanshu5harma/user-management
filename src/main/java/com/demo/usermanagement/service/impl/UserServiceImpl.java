package com.demo.usermanagement.service.impl;

import com.demo.usermanagement.model.UserEntity;
import com.demo.usermanagement.repository.UserRepository;
import com.demo.usermanagement.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * The Encoder.
     */
    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User Not Found"));
    }

    @Override
    public UserEntity getUserByUserName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(()-> new EntityNotFoundException("User Not Found"));
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        if(user.getId() == null) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
//        user.setPassword(encoder.encode("test"));
        return userRepository.save(user);
    }
}
