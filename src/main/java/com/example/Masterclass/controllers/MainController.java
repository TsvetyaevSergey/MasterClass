package com.example.Masterclass.controllers;

import com.example.Masterclass.models.UserDTO;
import com.example.Masterclass.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username","Пизда");
        }

        return "main-page";
    }
}//
