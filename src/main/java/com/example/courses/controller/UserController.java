package com.example.courses.controller;

import com.example.courses.model.AppUser;
import com.example.courses.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    private final AppUserService appUserService;

    @Autowired
    public UserController(AppUserService appUserService){
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<AppUser> getUsers(){
        return appUserService.getUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        return this.appUserService.autenticarUsuario(email, password);
    }

    @PostMapping("/registro")
    public ResponseEntity<Object> registrarUsuario(@RequestBody AppUser user){
        return this.appUserService.newUser(user);
    }

    @PutMapping("/perfil/{userId}")
    public ResponseEntity<Object> actualizarUsuario(@PathVariable("userId") Long id, @RequestBody AppUser user){
        return this.appUserService.updateUser(id, user);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Object> eliminarUsuario(@PathVariable("userId") Long id){
        return this.appUserService.deleteUser(id);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email) {
        return this.appUserService.findUserByEmail(email);
    }


}
