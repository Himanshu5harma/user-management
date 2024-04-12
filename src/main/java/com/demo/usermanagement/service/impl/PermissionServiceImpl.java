package com.demo.usermanagement.service.impl;

import com.demo.usermanagement.model.Permission;
import com.demo.usermanagement.repository.PermissionRepository;
import com.demo.usermanagement.repository.RolesRepository;
import com.demo.usermanagement.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Permission service.
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

    @Override
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public void deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id).orElse(null);
        if(permission != null){
            permission.getRoles().forEach(role -> {
                role.setPermissions( role.getPermissions().stream().filter(permission1 -> !permission1.equals(permission)).collect(Collectors.toSet()));
                rolesRepository.save(role);
            });
        }
        permissionRepository.deleteById(id);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }
}
