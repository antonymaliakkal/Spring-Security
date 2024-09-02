package com.example.securitex.service;

import com.example.securitex.model.UserPrincipal;
import com.example.securitex.model.Users;
import com.example.securitex.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetails loadUserByUsername");
        Users users = userRepository.findByName(username);
        System.out.println(users);
        if(users == null){
            System.out.println("Users not found");
            throw  new UsernameNotFoundException("Users not found");
        }
        return new UserPrincipal(users);
    }
}
