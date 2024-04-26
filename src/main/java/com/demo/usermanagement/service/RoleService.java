package com.demo.usermanagement.service;

import com.demo.usermanagement.model.Permission;
import com.demo.usermanagement.model.Role;

import java.util.List;
import java.util.Map;

/**
 * The RoleService interface provides methods for managing roles and permissions.
 */
public interface RoleService {

    /**
     * Gets all roles.
     *
     * @return a list of all roles in the system
     */
    public List<Role> getAllRoles();

    /**
     * Gets all roles permissions.
     *
     * @return a map of all roles and their associated permissions
     */
    public Map<String, Object> getAllRolesPermissions();

    /**
     * Creates a new permission.
     *
     * @param permission the permission to be created
     * @return the created permission
     */
    public Permission createPermission(Permission permission);

    /**
     * Creates a new role.
     *
     * @param role the role to be created
     * @return the created role
     */
    public Role createRole(Role role);

    /**
     * Deletes a role by its ID.
     *
     * @param id the ID of the role to be deleted
     */
    public void deleteById(Long id);
}
