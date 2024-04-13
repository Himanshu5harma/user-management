package com.demo.usermanagement.controller;

import com.demo.usermanagement.model.UserEntity;
import com.demo.usermanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Get all users list.
     *
     * @return the list
     */
    @GetMapping("/find-all")
    public List<UserEntity> getAllUsers(){
        logger.info("Received request at endpoint: /api/v1/users/find-all");
        return userService.getAllUsers();
    }

    /**
     * Get user user entity.
     *
     * @param id the id
     * @return the user entity
     */
    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable Long id){
        logger.info("Received request at endpoint: /api/v1/users/{}",id);
        return userService.getUserById(id);
    }

    /**
     * Get user user entity.
     *
     * @param userName the user name
     * @return the user entity
     */
    @GetMapping("/user-Name/{userName}")
    public UserEntity getUser(@PathVariable String userName){
        logger.info("Received request at endpoint: /api/v1/users/user-Name/{}",userName);
        return userService.getUserByUserName(userName);
    }

    /**
     * Create user user entity.
     *
     * @param user the user
     * @return the user entity
     */
    @PostMapping("/add-user")
    public UserEntity createUser(@RequestBody UserEntity user){
        logger.info("Received request at endpoint: /api/v1/users/add-user");
        return  userService.createUser(user);
    }

}
