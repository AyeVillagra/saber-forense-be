package com.example.courses.service;

import com.example.courses.model.Inscription;
import com.example.courses.repository.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InscriptionService {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    public List<Inscription> getInscriptionsByUserId(Long userId) {
        return inscriptionRepository.findByUserId(userId);
    }

    public Inscription createInscription(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    public void deleteInscription(Long id) {
        inscriptionRepository.deleteById(id);
    }
}

