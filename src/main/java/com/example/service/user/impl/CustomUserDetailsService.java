package com.example.service.user.impl;

import com.example.domain.training.account.TrainingUser;
import com.example.domain.training.account.repository.RoleMapper;
import com.example.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TrainingUser user = userService.getUserByName(username);
        if(user == null) {
        	throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        List<String> roles = roleMapper.selectRoleByUserId(user.getId());
        List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        
        return new User(user.getName(), user.getPassword(), authorities);
    }
}