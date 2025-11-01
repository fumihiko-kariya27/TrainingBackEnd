package com.example.auth;

import org.springframework.security.core.Authentication;

public interface TokenProvider {

	public String generateToken(Authentication authentication);
	
	public String getUsernameFrom(String token);
	
	public boolean validateToken(String authToken);
}
