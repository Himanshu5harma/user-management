package com.demo.usermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Auth request.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}
