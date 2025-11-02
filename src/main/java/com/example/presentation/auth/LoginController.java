package com.example.presentation.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.TokenProvider;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
    	log.info("Login Request recieved : " + loginRequest);
    	
    	try {
    		Authentication authentication = authenticationManager.authenticate(
    	        new UsernamePasswordAuthenticationToken(
    	            loginRequest.getUserId(),
    	            loginRequest.getPassword()
    	        )
    	    );

    	    String jwt = tokenProvider.generateToken(authentication);
    	    String userName = tokenProvider.getUsernameFrom(jwt);
    	    return ResponseEntity.ok(Map.of("user", userName, "accessToken", jwt));
    	} catch(BadCredentialsException | UsernameNotFoundException | DisabledException | LockedException e) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
    	}
    }
}