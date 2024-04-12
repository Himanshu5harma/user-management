package com.demo.usermanagement.controller;

import com.demo.usermanagement.model.Permission;
import com.demo.usermanagement.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Permission controller.
 */
@RestController
@RequestMapping("api/v1/permission")
@CrossOrigin(origins = "http://localhost:3000")
public class PermissionController {
    /**
     * The Permission service.
     */
    @Autowired
    PermissionService permissionService;

    /**
     * Create permission permission.
     *
     * @param permission the permission
     * @return the permission
     */
    @PostMapping("/create-permission")
    public Permission createPermission(@RequestBody Permission permission){
        return  permissionService.createPermission(permission);
    }

    /**
     * Delete permission list.
     *
     * @param id the permission id
     * @return the list
     */
    @DeleteMapping("/delete-permission/{id}")
    public List<Permission> deletePermission(@PathVariable Long id){
        permissionService.deletePermission(id);
        return permissionService.getAllPermissions();
    }

    /**
     * Get all permissions.
     */
    @GetMapping("/find-all")
    public void getAllPermissions(){
        permissionService.getAllPermissions();
    }
}
