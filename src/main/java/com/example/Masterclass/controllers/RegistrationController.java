package com.example.Masterclass.controllers;

import com.example.Masterclass.models.UserDTO;
import com.example.Masterclass.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/new-user")
    public String addUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String role, Model model) {
            UserDTO user = new UserDTO(username, passwordEncoder.encode(password), email, role);
            userRepository.save(user);
            return "redirect:/login";
    }
}
