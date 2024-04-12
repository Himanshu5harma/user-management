package com.demo.usermanagement.controller;

import com.demo.usermanagement.model.Permission;
import com.demo.usermanagement.model.Role;
import com.demo.usermanagement.service.RoleService;
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

    /**
     * Get all roles list.
     *
     * @return the list
     */
    @GetMapping("/find-all")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    /**
     * Get all roles permissions map.
     *
     * @return the map
     */
    @GetMapping("/find-all-roles-permissions")
    public Map<String, Object> getAllRolesPermissions(){
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
        roleService.deleteById(id);
        return roleService.getAllRolesPermissions();
    }

}
