package com.example.securitex.service;

import com.example.securitex.model.Users;
import com.example.securitex.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    public Users register(Users users){
        return userRepository.save(users);
    }

    public List<Users> getAll(){
        return userRepository.findAll();
    }

    public String verify(Users users) {
        Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(users.getName() , users.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(users.getName());
        }

        else {
            return "Failure";
        }

    }

}
