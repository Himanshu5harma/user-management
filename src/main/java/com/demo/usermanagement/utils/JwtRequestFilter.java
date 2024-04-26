package com.demo.usermanagement.utils;

import com.demo.usermanagement.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The JwtRequestFilter class handles JWT authentication for incoming requests.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    /**
     * Instantiates a new JwtRequestFilter.
     *
     * @param jwtUtil            the jwt util
     * @param userDetailsService the user details service
     */
    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
    /**
     * This method is responsible for JWT authentication for incoming requests.
     * It logs the authorization header, username, and any exceptions that occur.
     *
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @param chain The FilterChain object.
     * @throws ServletException If an exception occurs during the filter operation.
     * @throws IOException If an I/O exception occurs.
     * @throws java.io.IOException If an I/O exception occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, java.io.IOException {
        logger.debug("Filtering request with JWT authentication...");
        final String authorizationHeader = request.getHeader("Authorization");
        logger.debug("Authorization header: {}", authorizationHeader);

        String username = null;
        String jwt = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                logger.debug("Extracted JWT: {}", jwt);
                username = jwtUtil.extractUsername(jwt);
                logger.debug("Extracted username: {}", username);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                logger.debug("Loaded user details for username: {}", username);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    logger.debug("Authentication successful for username: {}", username);
                } else {
                    logger.warn("JWT token validation failed for username: {}", username);
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred during JWT authentication: {}", e.getMessage(), e);
        } finally {
            chain.doFilter(request, response);
        }
    }
}
