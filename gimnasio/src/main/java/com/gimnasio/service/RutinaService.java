package com.gimnasio.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gimnasio.model.Rutina;
import com.gimnasio.repository.RutinaRepository;

@Service
public class RutinaService {

    private final RutinaRepository rutinaRepository;

    public RutinaService(RutinaRepository rutinaRepository) {
        this.rutinaRepository = rutinaRepository;
    }

    public List<Rutina> listar() {
        return rutinaRepository.findAll();
    }

    public Optional<Rutina> buscarPorId(Long id) {
        return rutinaRepository.findById(id);
    }
    
    public List<Rutina> listarConEjercicios() {
        return rutinaRepository.findAllConEjercicios();
    }

    public Optional<Rutina> buscarConEjercicios(Long id) {
        return rutinaRepository.findByIdConEjercicios(id);
    }

    @Transactional
    public Rutina guardar(Rutina rutina) {
        return rutinaRepository.save(rutina);
    }

    // Al borrar la rutina se borra sus ejercicios todo en una transacción
    @Transactional
    public boolean eliminar(Long id) {
        Optional<Rutina> rutina = rutinaRepository.findById(id);
        if (rutina.isEmpty()) {
            return false;
        }
        rutinaRepository.delete(rutina.get());
        return true;
    }
}