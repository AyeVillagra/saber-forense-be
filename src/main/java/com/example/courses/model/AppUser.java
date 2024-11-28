package com.example.courses.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "El email es obligatorio.")
    @Email(message = "El email debe tener un formato válido.")
    private String email;

    @NotNull(message = "Debe ingresar una contraseña.")
    @Size(min = 8, message = "La contraseña debe contener al menos 8 caracteres.")
    private String password;

    @NotNull(message = "El nombre es obligatorio.")
    private String name;
    @NotNull(message = "El apellido es obligatorio.")
    private String lastName;
    private String address;
    @Pattern(regexp = "\\d+", message = "El número de dirección debe ser un número válido.")
    private String addressNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inscription> inscriptions;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public AppUser() {
    }

    public AppUser(String email, String password, String name, String lastName, String address, String addressNumber,
                   Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.addressNumber = addressNumber;
        this.role = role;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.toUpperCase();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address.toUpperCase();;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Inscription> getInscripciones() {
        return inscriptions;
    }

    public void setInscripciones(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Métodos de la interfaz UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Ajustar según lógica de negocio si es necesario
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Ajustar según lógica de negocio si es necesario
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Ajustar según lógica de negocio si es necesario
    }

    @Override
    public boolean isEnabled() {
        return true; // Ajustar según lógica de negocio si es necesario
    }

}


