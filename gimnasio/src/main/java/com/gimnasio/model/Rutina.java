package com.gimnasio.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 60, message = "El nombre no puede pasar de 60 caracteres")
    private String nombre;
    @Size(max = 255, message = "La descripción no puede pasar de 255 caracteres")
    private String descripcion;
    @NotBlank(message = "Elige un nivel")
    private String nivel;

    // Si borro la rutina los ejercicios se borran con ella
    @OneToMany(mappedBy = "rutina", cascade = CascadeType.REMOVE)
    private List<Ejercicio> ejercicios = new ArrayList<>();

    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }

    public String getNombre() {
    	return nombre;
    }
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }

    public String getDescripcion() {
    	return descripcion;
    }
    public void setDescripcion(String descripcion) {
    	this.descripcion = descripcion;
    }

    public String getNivel() {
    	return nivel;
    }
    public void setNivel(String nivel) {
    	this.nivel = nivel;
    }

    public List<Ejercicio> getEjercicios() {
    	return ejercicios;
    }
    public void setEjercicios(List<Ejercicio> ejercicios) {
    	this.ejercicios = ejercicios;
    }
}