package com.demo.usermanagement.config;

import com.demo.usermanagement.repository.UserRepository;
import com.demo.usermanagement.utils.JwtRequestFilter;
import com.demo.usermanagement.utils.UserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * This class represents the security configuration for the application.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * The JWT request filter used for authentication.
     */
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Token based login filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.securityMatcher("authenticate/**","api/v1/users/add-user","swagger-ui/**").authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("authenticate/**","api/v1/users/add-user","swagger-ui/**").permitAll()
                ).sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.securityMatcher("api/v1/**").authorizeHttpRequests(authorize ->
                authorize.requestMatchers("api/v1/**").authenticated()
                        .requestMatchers("api/v1/**").hasAnyRole("admin", "worker","supervisor"))
                .httpBasic(withDefaults());
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Returns a BCryptPasswordEncoder instance with a strength of 10.
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    /**
     * Bean for creating an AuthenticationManager.
     *
     * @param userDetailsService the service to load user details
     * @param passwordEncoder    the encoder to encode passwords
     * @param userRepository     the repository to manage users
     * @return the created AuthenticationManager
     * @throws Exception if an error occurs during creation
     */
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserRepository userRepository) throws Exception {
        AuthenticationProvider authenticationProvider =
                new UserAuthenticationProvider(userDetailsService, passwordEncoder, userRepository);
        return new ProviderManager(authenticationProvider);
    }
}
