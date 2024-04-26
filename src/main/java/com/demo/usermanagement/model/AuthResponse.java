package com.demo.usermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents an Auth response.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {
    /**
     * The JWT token.
     */
    private String jwt;
}

