package com.gimnasio.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gimnasio.model.Ejercicio;
import com.gimnasio.model.Rutina;
import com.gimnasio.repository.EjercicioRepository;
import com.gimnasio.repository.RutinaRepository;

@Service
public class EjercicioService {

    private final EjercicioRepository ejercicioRepository;
    private final RutinaRepository rutinaRepository;

    public EjercicioService(EjercicioRepository ejercicioRepository, RutinaRepository rutinaRepository) {
        this.ejercicioRepository = ejercicioRepository;
        this.rutinaRepository = rutinaRepository;
    }

    public List<Ejercicio> listar() {
        return ejercicioRepository.findAllConRutina();
    }

    public Optional<Ejercicio> buscarPorId(Long id) {
        return ejercicioRepository.findByIdConRutina(id);
    }

    // Devuelve false si la rutina elegida no existe
    @Transactional
    public boolean guardar(Ejercicio ejercicio) {
        if (ejercicio.getRutina() == null || ejercicio.getRutina().getId() == null) {
            return false;
        }
        Optional<Rutina> rutina = rutinaRepository.findById(ejercicio.getRutina().getId());
        if (rutina.isEmpty()) {
            return false;
        }
        ejercicio.setRutina(rutina.get());
        ejercicioRepository.save(ejercicio);
        return true;
    }

    // Borrar un ejercicio no toca su rutina
    @Transactional
    public boolean eliminar(Long id) {
        if (!ejercicioRepository.existsById(id)) {
            return false;
        }
        ejercicioRepository.deleteById(id);
        return true;
    }
}