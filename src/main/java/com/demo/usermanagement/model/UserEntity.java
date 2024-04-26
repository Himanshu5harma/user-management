package com.demo.usermanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user entity.
 */
@Table(name = "users")
@Entity
@Getter
@Setter
public class UserEntity {
    /**
     * The user's ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user's first name.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * The user's last name.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * The user's birth date.
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * The user's username.
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * The user's password.
     */
    private String password;

    /**
     * The user's roles.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}