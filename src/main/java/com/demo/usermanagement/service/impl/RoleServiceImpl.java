package com.demo.usermanagement.service.impl;

import com.demo.usermanagement.model.Permission;
import com.demo.usermanagement.model.Role;
import com.demo.usermanagement.repository.PermissionRepository;
import com.demo.usermanagement.repository.RolesRepository;
import com.demo.usermanagement.repository.UserRepository;
import com.demo.usermanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Role service.
 */
@Service
public class RoleServiceImpl implements RoleService {

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
        return rolesRepository.findAll();
    }

    @Override
    public Map<String, Object> getAllRolesPermissions() {
        List<Role> allRoles =  rolesRepository.findAll();
        List<Permission> allPermissions = permissionRepository.findAll();

        return Map.of("roles",allRoles,"permissions",allPermissions);
    }

    @Override
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Role createRole(Role role) {
        return rolesRepository.save(role);
    }

    @Override
    public void deleteById(Long id) {
        Role role = rolesRepository.findById(id).orElse(null);
        if(role != null){
            role.getPermissions().forEach(permission -> {
                permission.setRoles(permission.getRoles().stream().filter(role1 -> !role1.equals(role)).collect(Collectors.toSet()));
                permissionRepository.save(permission);
            });
            role.getUserEntities().forEach(user -> {
                user.setRoles(user.getRoles().stream().filter(role1 -> !role1.equals(role)).collect(Collectors.toSet()));
                userRepository.save(user);
            });
        }
        rolesRepository.deleteById(id);
    }
}
