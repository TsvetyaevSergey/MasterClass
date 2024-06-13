package com.example.Masterclass.repo;

import com.example.Masterclass.models.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDTO,Long> {
    Optional<UserDTO> findByName(String username);
}
