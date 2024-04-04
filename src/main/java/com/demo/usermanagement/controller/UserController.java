package com.demo.usermanagement.controller;

import com.demo.usermanagement.model.UserEntity;
import com.demo.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/find-all")
    public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/add-user")
    public UserEntity createUser(@RequestBody UserEntity user){
        return  userService.createUser(user);
    }

}
