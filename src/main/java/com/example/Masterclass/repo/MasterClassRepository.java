package com.example.Masterclass.repo;

import com.example.Masterclass.models.Dish;
import com.example.Masterclass.models.MasterClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterClassRepository extends JpaRepository<MasterClass,Long> {
}
