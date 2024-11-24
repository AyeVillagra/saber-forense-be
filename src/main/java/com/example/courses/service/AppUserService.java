package com.example.courses.service;

import com.example.courses.model.AppUser;
import com.example.courses.model.Role;
import com.example.courses.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppUserService  {
    HashMap<String, Object> datos;
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping
    public List<AppUser> getUsers() {
        return this.appUserRepository.findAll();
    }

    public ResponseEntity<Object> newUser(AppUser user) {
        Optional<AppUser> existingUser = appUserRepository.findAppUserByEmail(user.getEmail());
        datos = new HashMap<>();

        // Verifica si el usuario ya existe
        if (existingUser.isPresent()) {
            datos.put("error", true);
            datos.put("message", "Ya existe el email");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        user.setRole(Role.STUDENT);

// save()  método predefinido en Spring Data JPA.
// es posible usarlo siempre y cuando la interface UserRepository extienda de JpaRepository
        appUserRepository.save(user);
        datos.put("data", user);
        datos.put("message", "Usuario registrado con éxito");

        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deleteUser(Long id) {
        Map<String, Object> datos = new HashMap<>();

        boolean exists = this.appUserRepository.existsById(id);

        if (!exists) {
            datos.put("error", true);
            datos.put("message", "No existe el usuario con ese id");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
        appUserRepository.deleteById(id);
        datos.put("message", "Usuario eliminado con éxito");
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }



    public ResponseEntity<Object> updateUser(Long userId, AppUser user) {
        // Lógica para actualizar el usuario en la base de datos

        // Verifica si el usuario ya existe por correo electrónico antes de actualizar
        Optional<AppUser> existingUser = appUserRepository.findAppUserByEmail(user.getEmail());
        if (!existingUser.isPresent()) {
            // Usuario no encontrado, devuelve una respuesta de error
            datos.put("error", true);
            datos.put("message", "El usuario no existe");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
        // Actualiza la contraseña si se proporciona
        if (user.getPassword() != null) {
            existingUser.get().setPassword(user.getPassword());
        }

        // Actualiza los detalles adicionales si se proporcionan
        if (user.getName() != null) {
            existingUser.get().setName(user.getName());
        }

        if (user.getLastName() != null) {
            existingUser.get().setLastName(user.getLastName());
        }

        if (user.getAddress() != null) {
            existingUser.get().setAddress(user.getAddress());
        }

        if (user.getAddressNumber() != null) {
            existingUser.get().setAddressNumber(user.getAddressNumber());
        }

        // Guarda la actualización en la base de datos
        appUserRepository.save(existingUser.get());

        // Devuelve una respuesta de éxito
        datos.put("message", "Usuario actualizado con éxito");
        datos.put("data", existingUser.get());
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }


    public ResponseEntity<Object> autenticarUsuario(String email, String password) {
        Map<String, Object> datos = new HashMap<>();

        Optional<AppUser> user = appUserRepository.findAppUserByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            // Autenticación exitosa
            datos.put("message", "Autenticación exitosa");
            datos.put("data", user.get());
            datos.put("role", user.get().getRole());
            return new ResponseEntity<>(datos, HttpStatus.OK);
        } else {
            // Autenticación fallida
            datos.put("error", true);
            datos.put("message", "Credenciales incorrectas");
            return new ResponseEntity<>(datos, HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<Object> findUserByEmail(String email) {
        Optional<AppUser> user = appUserRepository.findAppUserByEmail(email);
        Map<String, Object> datos = new HashMap<>();

        if (user.isPresent()) {
            datos.put("data", user.get());
            return new ResponseEntity<>(datos, HttpStatus.OK);
        } else {
            datos.put("error", true);
            datos.put("message", "Usuario no encontrado");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }



}


