package com.example.Masterclass.controllers;

import com.example.Masterclass.models.*;
import com.example.Masterclass.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MasterClassController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServicesRepository servicesRepository;
    @Autowired
    MasterClassRepository masterClassRepository;
    @Autowired
    DishRepository dishRepository;
    @Autowired
    MasterRepository masterRepository;

    @GetMapping("/masterClasses")
    public String allMasterClasses(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username","");
        }
        List<MasterClass> masterClasses = masterClassRepository.findAll();
        model.addAttribute("masterClasses",masterClasses);
        return "masterClasses";
    }
    @GetMapping("/masterClassesByUser")
    public String allMasterClassesByUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
            List<MasterClass> masterClasses = masterClassRepository.findAll();
            ArrayList<MasterClass> masterClassesByUser = new ArrayList<>();
            for (MasterClass masterClass : masterClasses) {
                if (masterClass.getUserDTO().getId().equals(user.getId())) {
                    masterClassesByUser.add(masterClass);
                }
            }
            model.addAttribute("masterClasses", masterClassesByUser);
        }
        else {
            return "redirect://";
        }
        return "masterClasses";
    }

    @GetMapping("/masterClass/{id}")
    public String masterClass_detail(@PathVariable(value = "id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username","");
        }
        MasterClass masterClass = masterClassRepository.findById(id).orElseThrow();
        model.addAttribute("masterClass", masterClass);
        return "masterClass-detail";
    }


    @GetMapping("/masterClass-add")
    public String masterClass_add(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username", "");
        }

        // Загрузка всех блюд, услуг и мастеров
        List<Dish> dishes = dishRepository.findAll();
        List<Service> services = servicesRepository.findAll();
        List<Master> masters = masterRepository.findAll();

        model.addAttribute("dishes", dishes);
        model.addAttribute("services", services);
        model.addAttribute("masters", masters);

        return "masterClass-add";
    }

    @PostMapping("/save-masterClass")
    public String save_masterClass(Model model, @RequestParam String name,
                                   @RequestParam String wishes, @RequestParam Set<Long> dishes,
                                   @RequestParam Set<Long> services, @RequestParam Long masterId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());

            // Получение выбранных блюд и услуг по их ID
            Set<Dish> selectedDishes = new HashSet<>(dishRepository.findAllById(dishes));
            Set<Service> selectedServices = new HashSet<>(servicesRepository.findAllById(services));

            // Получение выбранного мастера по его ID
            Master selectedMaster = masterRepository.findById(masterId).orElseThrow();

            // Создание и сохранение нового мастер-класса
            MasterClass masterClass = new MasterClass(selectedMaster, selectedServices, selectedDishes, wishes, name, user);
            masterClassRepository.save(masterClass);
        } else {
            return "redirect:/";
        }
        return "redirect:/masterClassesByUser";
    }


    @GetMapping("/masterClass/edit/{id}")
    public String editMasterClassForm(@PathVariable Long id, Model model) {
        MasterClass masterClass = masterClassRepository.findById(id).orElseThrow();
        model.addAttribute("masterClass", masterClass);
        List<Dish> dishes = dishRepository.findAll();
        List<Service> services = servicesRepository.findAll();
        List<Master> masters = masterRepository.findAll();

        model.addAttribute("dishes", dishes);
        model.addAttribute("services", services);
        model.addAttribute("masters", masters);
        return "masterClass-edit";
    }

    @PostMapping("/masterClass/update/{id}")
    public String updateMasterClass(@PathVariable Long id, Model model, @RequestParam String name,
                                    @RequestParam String wishes, @RequestParam Set<Long> dishes,
                                    @RequestParam Set<Long> services, @RequestParam Long masterId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());

            // Получение выбранных блюд и услуг по их ID
            Set<Dish> selectedDishes = new HashSet<>(dishRepository.findAllById(dishes));
            Set<Service> selectedServices = new HashSet<>(servicesRepository.findAllById(services));

            // Получение выбранного мастера по его ID
            Master selectedMaster = masterRepository.findById(masterId).orElseThrow();

            // Создание и сохранение нового мастер-класса
            MasterClass masterClass = new MasterClass(selectedMaster, selectedServices, selectedDishes, wishes, name, user);
            masterClass.setId(id);
            masterClassRepository.save(masterClass);
        } else {
            return "redirect:/";
        }
        return "redirect:/masterClassesByUser";
    }

    @GetMapping("/masterClass/delete/{id}")
    public String deleteMasterClass(@PathVariable Long id) {
        masterClassRepository.deleteById(id);
        return "redirect:/masterClasses";
    }
}
