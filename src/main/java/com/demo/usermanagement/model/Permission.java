package com.demo.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * The Permission class represents a permission that can be associated with a role in the system.
 * Each permission has a unique id and a name.
 * A permission can be associated with multiple roles.
 */
@Entity
@Table(name = "permissions")
@Getter
@Setter
public class Permission {
    /**
     * The id of the Permission.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the Permission.
     */
    private String name;

    /**
     * The roles associated with this Permission.
     */
    @ManyToMany(mappedBy = "permissions", cascade = CascadeType.REFRESH)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();
}
