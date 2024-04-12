package com.demo.usermanagement.service;

import com.demo.usermanagement.model.Permission;

import java.util.List;

/**
 * The interface Permission service.
 */
public interface PermissionService {
    /**
     * Create permission permission.
     *
     * @param permission the permission
     * @return the permission
     */
    public Permission createPermission(Permission permission);

    /**
     * Delete permission.
     *
     * @param id the id
     */
    public void deletePermission(Long id);

    /**
     * Gets all permissions.
     *
     * @return the all permissions
     */
    public List<Permission> getAllPermissions();
}
