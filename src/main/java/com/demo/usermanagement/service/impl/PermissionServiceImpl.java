package com.demo.usermanagement.service.impl;

import com.demo.usermanagement.model.Permission;
import com.demo.usermanagement.repository.PermissionRepository;
import com.demo.usermanagement.repository.RolesRepository;
import com.demo.usermanagement.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class represents the Permission service.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    /**
     * The Permission repository.
     */
    @Autowired
    PermissionRepository permissionRepository;

    /**
     * The Roles repository.
     */
    @Autowired
    RolesRepository rolesRepository;

        private final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    /**
     * This method creates a new permission in the system.
     *
     * @param permission The permission object to be created.
     * @return The newly created permission.
     * @throws RuntimeException If a permission with the same name already exists.
     */
    @Override
    public Permission createPermission(Permission permission) {
        try {
            Optional<Permission> checkPermission = permissionRepository.findByName(permission.getName());

            if(checkPermission.isPresent()){
                throw  new RuntimeException("Permission Already Exist With Same Name.");
            }

            return permissionRepository.save(permission);
        } catch (RuntimeException e) {
            logger.error("Failed to create permission: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Deletes a permission by its ID. Removes the permission from all roles it is associated with and deletes it from the database.
     *
     * @param id the ID of the permission to delete
     */
    @Override
    public void deletePermission(Long id) {
        try {
            Permission permission = permissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Permission not found with id: " + id));

            permission.getRoles().forEach(role -> {
                role.setPermissions(role.getPermissions().stream().filter(permission1 -> !permission1.equals(permission)).collect(Collectors.toSet()));
                rolesRepository.save(role);
            });

            permissionRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Failed to delete permission: " + e.getMessage(), e);
            throw new RuntimeException("Failed to delete permission", e);
        }
    }

    /**
     * Gets all permissions.
     *
     * @return the list of all permissions
     */
    @Override
    public List<Permission> getAllPermissions() throws RuntimeException {
        try {
            return permissionRepository.findAll();
        } catch (RuntimeException e) {
            logger.error("Failed to retrieve permissions: " + e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve permissions", e);
        }
    }
}
