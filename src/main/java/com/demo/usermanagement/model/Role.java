package com.demo.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;



/**
 * Represents a role in a system.
 */
@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {
    /**
     * The unique identifier of the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the role.
     */
    private String name;

    /**
     * The users associated with this role.
     */
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<UserEntity> userEntities = new HashSet<>();

    /**
     * The permissions associated with this role.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();
}
