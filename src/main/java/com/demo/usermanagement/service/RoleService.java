package com.demo.usermanagement.service;

import com.demo.usermanagement.model.Permission;
import com.demo.usermanagement.model.Role;

import java.util.List;
import java.util.Map;

/**
 * The interface Role service.
 */
public interface RoleService {
    /**
     * Gets all roles.
     *
     * @return the all roles
     */
    public List<Role> getAllRoles();

    /**
     * Gets all roles permissions.
     *
     * @return the all roles permissions
     */
    public Map<String, Object> getAllRolesPermissions();

    /**
     * Create permission permission.
     *
     * @param permission the permission
     * @return the permission
     */
    public Permission createPermission(Permission permission);

    /**
     * Create role role.
     *
     * @param role the role
     * @return the role
     */
    public Role createRole(Role role);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    public void deleteById(Long id);
}
