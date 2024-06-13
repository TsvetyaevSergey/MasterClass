package com.example.Masterclass.controllers;

import com.example.Masterclass.models.Service;
import com.example.Masterclass.models.UserDTO;
import com.example.Masterclass.repo.ServicesRepository;
import com.example.Masterclass.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ServiceController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServicesRepository servicesRepository;

    @GetMapping("/services")
    public String allServices(Model model, @RequestParam(required = false) String search) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username","");
        }
        List<Service> services;
        if (search != null && search.length() >= 3) {
            services = servicesRepository.findByNameStartingWith(search);
        } else {
            services = servicesRepository.findAll();
        }
        model.addAttribute("services", services);
        model.addAttribute("search", search); // Чтобы сохранить значение в поле поиска
        return "services";
    }

    @GetMapping("/service/{id}")
    public String service_detail(@PathVariable(value = "id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username","");
        }
        Service service = servicesRepository.findById(id).orElseThrow();
        model.addAttribute("service", service);
        return "service-detail";
    }

    @GetMapping("/service-add")
    public String service_add(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username","");
        }
        return "service-add";
    }

    @PostMapping("/save-service")
    public String save_service(Model model, @RequestParam String name, @RequestParam int price,
                               @RequestParam String description, @RequestParam String image_url) {
        Service service = new Service(description, price, name, image_url);
        servicesRepository.save(service);
        return "redirect:/services";
    }

    @GetMapping("/service/edit/{id}")
    public String editServiceForm(@PathVariable Long id, Model model) {
        Service service = servicesRepository.findById(id).orElseThrow();
        model.addAttribute("service", service);
        return "service-edit";
    }

    @PostMapping("/service/update/{id}")
    public String updateService(@PathVariable Long id, @ModelAttribute Service service) {
        Service service1 = servicesRepository.findById(id).orElseThrow();
        service1.setDescription(service.getDescription());
        service1.setName(service.getName());
        service1.setImage_url(service.getImage_url());
        service1.setPrice(service.getPrice());
        servicesRepository.save(service1);
        return "redirect:/service/" + id;
    }

    @GetMapping("/service/delete/{id}")
    public String deleteService(@PathVariable Long id) {
        servicesRepository.deleteById(id);
        return "redirect:/services";
    }
}
