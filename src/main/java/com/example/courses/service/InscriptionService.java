package com.example.courses.service;

import com.example.courses.model.Inscription;
import com.example.courses.repository.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public void deactivateInscription(Long id) {
        Optional<Inscription> inscriptionOptional = inscriptionRepository.findById(id);
        if (inscriptionOptional.isPresent()) {
            Inscription inscription = inscriptionOptional.get();
            inscription.setActive(false);  // Cambiar el estado a inactivo
            inscriptionRepository.save(inscription);  // Guardar la inscripción con el nuevo estado
        } else {
            throw new RuntimeException("Inscripción no encontrada.");
        }
    }

}

