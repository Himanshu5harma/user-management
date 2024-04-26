package com.demo.usermanagement.service;

import com.demo.usermanagement.model.Permission;

import java.util.List;

/**
 * This interface represents a service for managing permissions.
 * It provides methods to create, delete, and retrieve permissions.
 */
public interface PermissionService {
    /**
     * Creates a new permission.
     *
     * @param permission the permission to be created.
     * @return the created permission.
     */
    public Permission createPermission(Permission permission);

    /**
     * Deletes a permission by its ID.
     *
     * @param id the ID of the permission to be deleted.
     */
    public void deletePermission(Long id);

    /**
     * Retrieves all permissions.
     *
     * @return a list of all permissions.
     */
    public List<Permission> getAllPermissions();
}
