package com.freelapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.freelapp.model.Roles;
import com.freelapp.model.User;
import com.freelapp.repository.RolesRepository;
import com.freelapp.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;
    
    public User createUser(User user) {
        // Assicura che ci sia sempre un ruolo
        if (user.getRole() == null) {
            Roles defaultRole = rolesRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("Ruolo USER non trovato. Assicurati che esista un ruolo con id=1"));
            user.setRole(defaultRole);
        }
        return userRepository.save(user);
    }
    
    public User updateUser(User user) {
        // Mantieni il ruolo esistente se non specificato
        if (user.getRole() == null && user.getId() > 0) {
            User existingUser = userRepository.findById(user.getId()).orElse(null);
            if (existingUser != null) {
                user.setRole(existingUser.getRole());
            }
        }
        return userRepository.save(user);
    }
}
