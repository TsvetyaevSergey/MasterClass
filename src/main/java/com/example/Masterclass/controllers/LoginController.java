package com.example.Masterclass.controllers;

import com.example.Masterclass.models.UserDTO;
import com.example.Masterclass.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

}
