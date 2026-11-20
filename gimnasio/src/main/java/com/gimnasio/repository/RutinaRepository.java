package com.gimnasio.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.gimnasio.model.Rutina;

public interface RutinaRepository extends JpaRepository<Rutina, Long> {

    // Carga los ejercicios para poder contarlos en la plantilla
    @Query("select r from Rutina r left join fetch r.ejercicios order by r.nombre")
    List<Rutina> findAllConEjercicios();

    @Query("select r from Rutina r left join fetch r.ejercicios where r.id = :id")
    Optional<Rutina> findByIdConEjercicios(@Param("id") Long id);
}