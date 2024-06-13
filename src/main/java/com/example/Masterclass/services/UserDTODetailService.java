package com.example.Masterclass.services;

import com.example.Masterclass.config.UserDTODetails;
import com.example.Masterclass.models.UserDTO;
import com.example.Masterclass.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDTODetailService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDTO> user = repository.findByName(username);
        return user.map(UserDTODetails::new).orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
    }
}
