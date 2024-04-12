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
 * The type Security config.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Api filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    @Order(2)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.securityMatcher("api/v1/")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                ).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).httpBasic(withDefaults());
        return http.build();
    }

    /**
     * Form login filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.securityMatcher("api/v1/**").authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                ).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }

    /**
     * Token based login filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain tokenBasedLoginFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.securityMatcher("authenticate/**","api/v1/users/add-user").authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                ).sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Password encoder b crypt password encoder.
     *
     * @return the b crypt password encoder
     */
    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder(10);
    }

    /**
     * Authentication manager authentication manager.
     *
     * @param userDetailsService the user details service
     * @param passwordEncoder    the password encoder
     * @param userRepository     the user repository
     * @return the authentication manager
     * @throws Exception the exception
     */
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserRepository userRepository) throws Exception {
        AuthenticationProvider authenticationProvider = new UserAuthenticationProvider(userDetailsService,passwordEncoder, userRepository);
        return new ProviderManager(authenticationProvider);
    }
}
