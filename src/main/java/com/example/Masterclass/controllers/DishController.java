package com.example.Masterclass.controllers;

import com.example.Masterclass.models.Dish;
import com.example.Masterclass.models.UserDTO;
import com.example.Masterclass.repo.DishRepository;
import com.example.Masterclass.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DishController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DishRepository dishRepository;

    @GetMapping("/dishes")
    public String allDishes(Model model, @RequestParam(required = false) String search) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username","");
        }
        List<Dish> dishes;
        if (search != null && search.length() >= 3) {
            dishes = dishRepository.findByNameStartingWith(search);
        } else {
            dishes = dishRepository.findAll();
        }
        model.addAttribute("dishes", dishes);
        model.addAttribute("search", search); // Чтобы сохранить значение в поле поиска
        return "dishes";
    }

    @GetMapping("/dish/{id}")
    public String dish_detail(@PathVariable(value = "id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username","");
        }
        Dish dish = dishRepository.findById(id).orElseThrow();
        model.addAttribute("dish", dish);
        return "dish-detail";
    }

    @GetMapping("/dish-add")
    public String dish_add(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            String userName = authentication.getName();
            UserDTO user = userRepository.findByName(userName).orElseThrow();
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username","");
        }
        return "dish-add";
    }

    @PostMapping("/save-dish")
    public String save_dish(Model model, @RequestParam String name, @RequestParam int price,
                            @RequestParam String category, @RequestParam String image_url,
                            @RequestParam String description, @RequestParam String ingredients) {
        Dish dish = new Dish(name, description, ingredients, price, category, image_url);
        dishRepository.save(dish);
        return "redirect:/dishes";
    }

    @GetMapping("/dish/edit/{id}")
    public String editDishForm(@PathVariable Long id, Model model) {
        Dish dish = dishRepository.findById(id).orElseThrow();
        model.addAttribute("dish", dish);
        return "dish-edit";
    }

    @PostMapping("/dish/update/{id}")
    public String updateDish(@PathVariable Long id, @ModelAttribute Dish dish) {
        Dish dish1 = dishRepository.findById(id).orElseThrow();
        dish1.setCategory(dish.getCategory());
        dish1.setDescription(dish.getDescription());
        dish1.setName(dish.getName());
        dish1.setImage_url(dish.getImage_url());
        dish1.setIngredients(dish.getIngredients());
        dish1.setPrice(dish.getPrice());
        dishRepository.save(dish1);
        return "redirect:/dish/" + id;
    }

    @GetMapping("/dish/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishRepository.deleteById(id);
        return "redirect:/dishes";
    }
}
