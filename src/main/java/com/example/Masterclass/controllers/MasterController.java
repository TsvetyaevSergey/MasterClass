package com.example.Masterclass.controllers;

import com.example.Masterclass.models.Dish;
import com.example.Masterclass.models.Master;
import com.example.Masterclass.models.UserDTO;
import com.example.Masterclass.repo.DishRepository;
import com.example.Masterclass.repo.MasterRepository;
import com.example.Masterclass.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class MasterController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MasterRepository masterRepository;

    @GetMapping("/masters")
    public String allMasters(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username","");
        }
        List<Master> masters = masterRepository.findAll();
        model.addAttribute("masters", masters);
        return "masters";
    }

    @GetMapping("/master/{id}")
    public String master_detail(@PathVariable(value = "id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username","");
        }
        Master master = masterRepository.findById(id).orElseThrow();
        model.addAttribute("master", master);
        return "master-detail";
    }


    @GetMapping("/master-add")
    public String master_add(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username","");
        }
        return "master-add";
    }

    @PostMapping("/save-master")
    public String save_master(Model model, @RequestParam String name, @RequestParam int price,
                            @RequestParam String achievements, @RequestParam String image_url,
                            @RequestParam String description)
    {
        Master master = new Master(name,achievements,description,price,image_url);
        masterRepository.save(master);
        return "redirect:/masters";
    }

    @GetMapping("/master/edit/{id}")
    public String editMasterForm(@PathVariable Long id, Model model) {
        Master master = masterRepository.findById(id).orElseThrow();
        model.addAttribute("master", master);
        return "master-edit";
    }

    @PostMapping("/master/update/{id}")
    public String updateMaster(@PathVariable Long id, @ModelAttribute Master master) {
        Master master1 = masterRepository.findById(id).orElseThrow();
        master1.setAchievements(master.getAchievements());
        master1.setDescription(master.getDescription());
        master1.setName(master.getName());
        master1.setImage_url(master.getImage_url());
        master1.setPrice(master.getPrice());
        masterRepository.save(master1);
        return "redirect:/master/" + id;
    }

    @GetMapping("/master/delete/{id}")
    public String deleteMaster(@PathVariable Long id) {
        masterRepository.deleteById(id);
        return "redirect:/masters";
    }
}

