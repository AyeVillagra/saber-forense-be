package com.example.courses.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inscription> inscriptions;

    @Lob
    @Column(columnDefinition = "TEXT") // Usamos TEXT para permitir almacenar cadenas largas de JSON
    @JsonIgnore
    private String imageUrlsJson;

    public Course() {}

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }
    public String getImageUrlsJson() {
        return imageUrlsJson;
    }

    public void setImageUrlsJson(String imageUrlsJson) {
        this.imageUrlsJson = imageUrlsJson;
    }

    // Método para convertir la lista de URLs a formato JSON
    private String convertToJson(List<String> imageUrls) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(imageUrls); // Convierte la lista a JSON
        } catch (Exception e) {
            e.printStackTrace();
            return "[]"; // Si ocurre un error, devolvemos una lista vacía
        }
    }

    // Método para convertir el JSON de nuevo a una lista de URLs
    public List<String> getImageUrls() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(imageUrlsJson, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Si ocurre un error, devolvemos una lista vacía
        }
    }
}
