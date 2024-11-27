package com.example.courses.controller;

import com.example.courses.model.AppUser;
import com.example.courses.model.LoginRequest;
import com.example.courses.service.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/usuarios")
public class AppUserController {
    private final AppUserService appUserService;
    private final AuthenticationManager authManager;

    // Constructor con inyección de dependencias
    @Autowired
    public AppUserController(AppUserService appUserService, AuthenticationManager authManager) {
        this.appUserService = appUserService;
        this.authManager = authManager;
    }

    @GetMapping
    public List<AppUser> getUsers(){
        return appUserService.getUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        try {
            // Crear un token de autenticación basado en las credenciales proporcionadas
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, password);

            // Validar las credenciales usando el AuthenticationManager
            Authentication authentication = authManager.authenticate(authenticationToken);

            // Si la autenticación es exitosa, devolver una respuesta en formato JSON
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario autenticado correctamente");
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            // Si las credenciales no son válidas, devolver un error en formato JSON
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @GetMapping("/verify-session")
    public ResponseEntity<Object> verifySession(HttpServletRequest request) {
        // Aquí verificas si hay una sesión activa, por ejemplo, usando Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            AppUser user = (AppUser) authentication.getPrincipal();
            return ResponseEntity.ok(user); // Retorna los datos del usuario autenticado
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
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
