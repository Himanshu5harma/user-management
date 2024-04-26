package com.demo.usermanagement.utils;

import com.demo.usermanagement.model.UserEntity;
import com.demo.usermanagement.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides utility methods for working with JSON Web Tokens (JWT).
 */
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * The User service.
     */
    @Autowired
    UserService userService;

    /**
     * The secret key used for signing JWTs.
     */
    private static final String SECRET_KEY = "ABCDEFGHIJKLMNOPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP";

    /**
     * Generates a JWT for the given user details.
     *
     * @param userDetails The user details for which to generate the token.
     * @return The generated JWT.
     * @throws UsernameNotFoundException If the user details cannot be found.
     */
    public String generateToken(UserDetails userDetails) {
        try {
            logger.debug("Generating JWT for user: {}", userDetails.getUsername());
            UserEntity user = userService.getUserByUserName(userDetails.getUsername());
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + 3600000); // Token valid for 1 hour
            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", userDetails.getAuthorities());
            claims.put("sub", userDetails.getUsername());
            claims.put("firstName", user.getFirstName());
            claims.put("lastName", user.getLastName());

            String token = Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();

            logger.debug("Generated JWT: {}", token);
            return token;
        } catch (UsernameNotFoundException e) {
            logger.error("Failed to generate JWT for user: {}", userDetails.getUsername(), e);
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred while generating JWT for user: {}", userDetails.getUsername(), e);
            throw new RuntimeException("Failed to generate JWT", e);
        }
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token
     * @return the username as a string
     */
    public String extractUsername(String token) {
        try {
            String username = extractClaim(token, Claims::getSubject);
            logger.debug("Extracted username: {}", username);
            return username;
        } catch (Exception e) {
            logger.error("Error extracting username from token", e);
            throw new RuntimeException("Failed to extract username from token", e);
        }
    }
    /**
     * Extracts a claim from a JWT token.
     *
     * @param token The JWT token.
     * @param claimsResolver A function that extracts the desired claim from the claims.
     * @param <T> The type of the claim.
     * @return The extracted claim.
     * @throws JwtException If there is an error parsing or validating the token.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            logger.debug("Extracted claims: {}", claims);
            return claimsResolver.apply(claims);
        } catch (JwtException e) {
            logger.error("Error extracting claim from token", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error", e);
            throw new RuntimeException("Unexpected error", e);
        }
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token The JWT token.
     * @return The extracted claims.
     * @throws JwtException If there is an error parsing or validating the token.
     */
    private Claims extractAllClaims(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            logger.debug("Extracted all claims: {}", claims);
            return claims;
        } catch (JwtException e) {
            logger.error("Error extracting all claims from token", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error", e);
            throw new RuntimeException("Unexpected error", e);
        }
    }

    /**
     * Validates the JWT token for the given user details.
     *
     * @param token       The JWT token to validate.
     * @param userDetails The user details to compare with the token.
     * @return True if the token is valid, false otherwise.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            boolean isUsernameValid = username.equals(userDetails.getUsername());
            boolean isTokenNotExpired = !isTokenExpired(token);

            if (!isUsernameValid) {
                logger.warn("Username in token does not match user details");
            }
            if (!isTokenNotExpired) {
                logger.warn("Token is expired");
            }

            return isUsernameValid && isTokenNotExpired;
        } catch (Exception e) {
            logger.error("Error validating token", e);
            throw new RuntimeException("Failed to validate token", e);
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date.
     * @throws JwtException If there is an error parsing or validating the token.
     */
    private Date extractExpiration(String token) {
        try {
            Date expiration = extractClaim(token, Claims::getExpiration);
            logger.debug("Extracted expiration date: {}", expiration);
            return expiration;
        } catch (JwtException e) {
            logger.error("Error extracting expiration date from token", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error", e);
            throw new RuntimeException("Unexpected error", e);
        }
    }
}
