package com.demo.usermanagement.service.impl;

import com.demo.usermanagement.model.Permission;
import com.demo.usermanagement.model.Role;
import com.demo.usermanagement.repository.PermissionRepository;
import com.demo.usermanagement.repository.RolesRepository;
import com.demo.usermanagement.repository.UserRepository;
import com.demo.usermanagement.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class implements the `RoleService` interface and provides implementation for all the methods defined in it.
 * It uses the `RolesRepository` to interact with the database for role-related operations.
 */
@Service
public class RoleServiceImpl implements RoleService {

    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    /**
     * The Roles repository.
     */
    @Autowired
    RolesRepository rolesRepository;

    /**
     * The Permission repository.
     */
    @Autowired
    PermissionRepository permissionRepository;

    /**
     * The User repository.
     */
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Role> getAllRoles() {
        try {
            logger.debug("Retrieving all roles...");
            List<Role> allRoles = rolesRepository.findAll();
            logger.debug("Retrieved {} roles", allRoles.size());
            return allRoles;
        } catch (Exception e) {
            logger.error("Error retrieving all roles: {}", e.getMessage());
            throw new RuntimeException("Error retrieving all roles", e);
        }
    }

    /**
     * Retrieves all roles and permissions and returns them as a map with keys "roles" and "permissions".
     *
     * @return a Map<String, Object> containing the roles and permissions
     */
    @Override
    public Map<String, Object> getAllRolesPermissions() {
        try {
            logger.debug("Retrieving all roles...");
            List<Role> allRoles = rolesRepository.findAll();
            logger.debug("Retrieved {} roles", allRoles.size());

            logger.debug("Retrieving all permissions...");
            List<Permission> allPermissions = permissionRepository.findAll();
            logger.debug("Retrieved {} permissions", allPermissions.size());

            return Map.of("roles", allRoles, "permissions", allPermissions);
        } catch (Exception e) {
            logger.error("Error retrieving roles and permissions: {}", e.getMessage());
            throw new RuntimeException("Error retrieving roles and permissions", e);
        }
    }

    /**
     * Creates a new permission in the system.
     *
     * @param permission The permission object to be created
     * @return The newly created permission
     * @throws RuntimeException if the permission with the same name already exists
     */
    @Override
    public Permission createPermission(Permission permission) {
        try {
            Optional<Permission> checkPermission = permissionRepository.findByName(permission.getName());

            if (checkPermission.isPresent()) {
                logger.error("Permission already exists with the same name: {}", permission.getName());
                throw new RuntimeException("Permission already exists with the same name");
            }

            logger.debug("Saving permission: {}", permission.getName());
            Permission savedPermission = permissionRepository.save(permission);
            logger.debug("Permission saved successfully: {}", savedPermission.getName());

            return savedPermission;
        } catch (Exception e) {
            logger.error("Failed to create permission: " + e.getMessage(), e);
            throw new RuntimeException("Failed to create permission", e);
        }
    }

    /**
     * Creates a new role in the system.
     *
     * @param  role  the role object to be created
     * @return       the newly created role
     * @throws RuntimeException if a role with the same name already exists
     */
    @Override
    public Role createRole(Role role) {
        try {
            if (role.getId() == null) {
                Optional<Role> checkRole = rolesRepository.findByName(role.getName());
                if (checkRole.isPresent()) {
                    logger.error("Role already exists with the same name: {}", role.getName());
                    throw new RuntimeException("A Role already exists with the same name");
                }
            }

            logger.debug("Saving role: {}", role.getName());
            Role savedRole = rolesRepository.save(role);
            logger.debug("Role saved successfully: {}", savedRole.getName());

            return savedRole;
        } catch (Exception e) {
            logger.error("Error creating role: {}", e.getMessage());
            throw new RuntimeException("Error creating role", e);
        }
    }
    /**
     * Deletes a role by its ID.
     *
     * @param  id  the ID of the role to delete
     */
    @Override
    public void deleteById(Long id) {
        try {
            Role role = rolesRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));

            role.getPermissions().forEach(permission -> {
                permission.getRoles().remove(role);
                permissionRepository.save(permission);
            });

            role.getUserEntities().forEach(user -> {
                user.getRoles().remove(role);
                userRepository.save(user);
            });

            rolesRepository.deleteById(id);
            logger.info("Role with id {} deleted successfully", id);
        } catch (Exception e) {
            logger.error("Error deleting role with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Error deleting role", e);
        }
    }
}
