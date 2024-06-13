package com.example.Masterclass.repo;

import com.example.Masterclass.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicesRepository extends JpaRepository<Service,Long> {
    List<Service> findByNameStartingWith(String prefix);
}
