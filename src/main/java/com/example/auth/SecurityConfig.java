package com.example.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOncePerRequestFilter authenticationFilter;
    private final AuthenticationEntryPoint customEntryPoint;

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    public SecurityConfig(CustomOncePerRequestFilter authenticationFilter, AuthenticationEntryPoint unauthorizedHandler) {
        this.authenticationFilter = authenticationFilter;
        this.customEntryPoint = unauthorizedHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	.cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            // サーバーはスケーリングを考慮してステートレスとする
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(customEntryPoint))
            .authorizeHttpRequests(authorize -> authorize
            	.requestMatchers("/login/**").permitAll()
            	.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            	.anyRequest().authenticated()
            )
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration config = new CorsConfiguration();
    	config.setAllowedOrigins(List.of(this.allowedOrigins));
    	config.setAllowedMethods(List.of("GET", "POST"));
    	config.setAllowedHeaders(List.of(""));
    	config.setAllowCredentials(true);
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", config);
    	return source;
    }
}
