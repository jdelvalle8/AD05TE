package com.gimnasio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 60, message = "El nombre no puede pasar de 60 caracteres")
    private String nombre;
    @NotNull(message = "Indica las series")
    @Min(value = 1, message = "Mínimo 1 serie")
    @Max(value = 10, message = "Máximo 10 series")
    private Integer series;
    @NotNull(message = "Indica las repeticiones")
    @Min(value = 1, message = "Mínimo 1 repetición")
    @Max(value = 50, message = "Máximo 50 repeticiones")
    private Integer repeticiones;
    
    // Cada ejercicio pertenece a una rutina
    @NotNull(message = "Elige una rutina")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rutina_id", nullable = false)
    private Rutina rutina;

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

    public Integer getSeries() {
        return series;
    }
    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getRepeticiones() {
        return repeticiones;
    }
    public void setRepeticiones(Integer repeticiones) {
        this.repeticiones = repeticiones;
    }

    public Rutina getRutina() {
        return rutina;
    }
    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }
}