package com.example.service.user.impl;

import com.example.domain.training.account.TrainingUser;
import com.example.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TrainingUser user = userService.getUserByName(username);
        if(user == null) {
        	throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getName(), user.getPassword(), Collections.emptyList());
    }
}