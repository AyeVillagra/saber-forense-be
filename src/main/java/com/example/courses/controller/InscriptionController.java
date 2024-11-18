package com.example.courses.controller;

import com.example.courses.model.Inscription;
import com.example.courses.service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inscripciones")
public class InscriptionController {
    private final InscriptionService inscriptionService;

    @Autowired
    public InscriptionController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    @GetMapping("/usuario/{userId}")
    public List<Map<String, Object>> getInscriptionsByUser(@PathVariable Long userId) {
        List<Inscription> inscriptions = inscriptionService.getInscriptionsByUserId(userId);
        return inscriptions.stream().map(inscription -> {
            Map<String, Object> response = new HashMap<>();
            response.put("id", inscription.getId());
            response.put("courseName", inscription.getCourse().getName());
            response.put("inscriptionDate", inscription.getInscriptionDate());
            response.put("active", inscription.isActive());
            return response;
        }).collect(Collectors.toList());
    }

}

