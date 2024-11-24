package com.example.courses.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.courses.repository.AppUserRepository;
import com.example.courses.model.AppUser;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar al usuario en la base de datos por su nombre de usuario
        AppUser appUser = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String role = appUser.getRole().name();

        // Devolver el usuario con la contraseña encriptada y roles
        return User.builder()
                .username(appUser.getName())
                .password(appUser.getPassword()) // Contraseña encriptada
                .roles(role) // Asumimos que tienes roles
                .build();
    }
}

