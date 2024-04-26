package com.demo.usermanagement.controller;

import com.demo.usermanagement.model.Permission;
import com.demo.usermanagement.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Permission Controller class.
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

    private final Logger logger = LoggerFactory.getLogger(PermissionController.class);

    /**
     * Create permission permission.
     *
     * @param permission the permission
     * @return the permission
     */
    @PostMapping("/create-permission")
    @PreAuthorize("hasRole('admin')")
    public Permission createPermission(@RequestBody Permission permission){
        logger.info("Received request at endpoint: /api/v1/permission/create-permission");
        return  permissionService.createPermission(permission);
    }

    /**
     * Delete permission list.
     *
     * @param id the permission id
     * @return the list
     */
    @DeleteMapping("/delete-permission/{id}")
    @PreAuthorize("hasRole('admin')")
    public List<Permission> deletePermission(@PathVariable Long id){
        logger.info("Received request at endpoint: /api/v1/permission/delete-permission/{}",id);
        permissionService.deletePermission(id);
        return permissionService.getAllPermissions();
    }

    /**
     * Get all permissions.
     */
    @GetMapping("/find-all")
    @PreAuthorize("hasAnyRole('admin','supervisor')")
    public List<Permission> getAllPermissions(){
        logger.info("Received request at endpoint: /api/v1/permission/find-all");
        return permissionService.getAllPermissions();
    }
}
