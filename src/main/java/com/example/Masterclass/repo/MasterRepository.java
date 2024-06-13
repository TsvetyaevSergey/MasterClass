package com.example.Masterclass.repo;

import com.example.Masterclass.models.Dish;
import com.example.Masterclass.models.Master;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterRepository extends JpaRepository<Master,Long> {
}
