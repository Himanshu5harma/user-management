package com.demo.usermanagement.service;

import com.demo.usermanagement.model.UserEntity;

import java.util.List;

public interface UserService {
    public List<UserEntity> getAllUsers();
    public UserEntity createUser(UserEntity user);
}
