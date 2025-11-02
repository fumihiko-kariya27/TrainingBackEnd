package com.example.presentation.auth;

import lombok.Data;

@Data
public class LoginRequest {
	
	private String userId;
    private String password;
    
    @Override
    public String toString() {
    	return String.format("ログインID : [%s], パスワード : [%s]", this.userId, this.password);
    }
}
