package com.example.securitex.service;

import com.example.securitex.model.Users;
import com.example.securitex.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users register(Users users){
        return userRepository.save(users);
    }

}
