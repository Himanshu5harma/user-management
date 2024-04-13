package com.demo.usermanagement.controller;

import com.demo.usermanagement.model.Permission;
import com.demo.usermanagement.model.Role;
import com.demo.usermanagement.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * The type Role controller.
 */
@RestController
@RequestMapping("api/v1/roles")
@CrossOrigin(origins = "http://localhost:3000")
public class RoleController {

    /**
     * The Role service.
     */
    @Autowired
    RoleService roleService;

    private final Logger logger = LoggerFactory.getLogger(RoleController.class);

    /**
     * Get all roles list.
     *
     * @return the list
     */
    @GetMapping("/find-all")
    public List<Role> getAllRoles(){
        logger.info("Received request at endpoint: /api/v1/roles/find-all");
        return roleService.getAllRoles();
    }

    /**
     * Get all roles permissions map.
     *
     * @return the map
     */
    @GetMapping("/find-all-roles-permissions")
    public Map<String, Object> getAllRolesPermissions(){
        logger.info("Received request at endpoint: /api/v1/roles/find-all-roles-permissions");
        return roleService.getAllRolesPermissions();
    }

    /**
     * Create permission permission.
     *
     * @param permission the permission
     * @return the permission
     */
    @PostMapping("/add-permission")
    public Permission createPermission(@RequestBody Permission permission){
        logger.info("Received request at endpoint: /api/v1/roles/add-permissions");
        return  roleService.createPermission(permission);
    }

    /**
     * Create role role.
     *
     * @param role the role
     * @return the role
     */
    @PostMapping("/create-role")
    public Role createRole(@RequestBody Role role){
        logger.info("Received request at endpoint: /api/v1/roles/create-role");
        return  roleService.createRole(role);
    }

    /**
     * Delete role map.
     *
     * @param id the id
     * @return the map
     */
    @DeleteMapping("/delete-role/{id}")
    public Map<String, Object> deleteRole(@PathVariable Long id){
        logger.info("Received request at endpoint: /api/v1/roles/delete-role/{}",id);
        roleService.deleteById(id);
        return roleService.getAllRolesPermissions();
    }

}
