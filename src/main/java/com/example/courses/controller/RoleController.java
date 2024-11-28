package com.example.courses.controller;


import com.example.courses.model.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/roles")
public class RoleController {

    @GetMapping
    public ResponseEntity<List<String>> getRoles() {
        // Obtener los roles del enum y convertirlos en una lista de strings
        List<String> roles = Arrays.stream(Role.values())
                .map(Enum::name)  // Convierte cada valor del enum a su nombre (string)
                .collect(Collectors.toList());

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
