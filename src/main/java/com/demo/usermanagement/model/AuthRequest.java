package com.demo.usermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an authentication request.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {
    /**
     * The username for the authentication request.
     */
    private String username;

    /**
     * The password for the authentication request.
     */
    private String password;
}
