package com.example.Masterclass.repo;

import com.example.Masterclass.models.Dish;
import com.example.Masterclass.models.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish,Long> {
    List<Dish> findByNameStartingWith(String prefix);
}
