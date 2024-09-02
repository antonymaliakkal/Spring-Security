package com.example.securitex.controller;

import com.example.securitex.model.Student;
import com.example.securitex.model.Users;
import com.example.securitex.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;


@RestController
@RequestMapping("/api")
public class SecurityController {

    private List<Student> studentList = new ArrayList<>(List.of(
            new Student(1 , "antony"),
            new Student(2 , "maliakkal")
    ));


    @Autowired
    UserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @GetMapping
    public List<Student> fuc(HttpServletRequest request){
        System.out.println("List<Student>");
        System.out.println("Session id : "  + request.getSession().getId());
        System.out.println((CsrfToken)request.getAttribute("_csrf"));
        return studentList;
    }

    @PostMapping
    public Users fun(@RequestBody Users users){

        String password = encoder.encode(users.getPassword());
        System.out.println(password);
        users.setPassword(password);
        return userService.register(users);

    }

}
