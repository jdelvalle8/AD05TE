package com.gimnasio.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.gimnasio.model.Ejercicio;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {

    // Carga la rutina para poder mostrar su nombre
    @Query("select e from Ejercicio e join fetch e.rutina order by e.nombre")
    List<Ejercicio> findAllConRutina();

    @Query("select e from Ejercicio e join fetch e.rutina where e.id = :id")
    Optional<Ejercicio> findByIdConRutina(@Param("id") Long id);
}