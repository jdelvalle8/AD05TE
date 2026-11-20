package com.gimnasio.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import com.gimnasio.model.Rutina;
import com.gimnasio.service.RutinaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/rutinas")
public class RutinaController {

    private static final List<String> NIVELES = List.of("Principiante", "Intermedio", "Avanzado");
    private final RutinaService rutinaService;

    public RutinaController(RutinaService rutinaService) {
        this.rutinaService = rutinaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("rutinas", rutinaService.listarConEjercicios());
        return "rutinas/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("rutina", new Rutina());
        model.addAttribute("niveles", NIVELES);
        return "rutinas/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Optional<Rutina> rutina = rutinaService.buscarPorId(id);
        if (rutina.isEmpty()) {
            redirect.addFlashAttribute("error", "La rutina no existe");
            return "redirect:/rutinas";
        }
        model.addAttribute("rutina", rutina.get());
        model.addAttribute("niveles", NIVELES);
        return "rutinas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("rutina") Rutina rutina, BindingResult result,
            Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("niveles", NIVELES);
            return "rutinas/form";
        }
        // Si ya existe, actualizo la fila que hay y no creo otra
        if (rutina.getId() != null) {
            Optional<Rutina> existente = rutinaService.buscarPorId(rutina.getId());
            if (existente.isEmpty()) {
                redirect.addFlashAttribute("error", "La rutina no existe");
                return "redirect:/rutinas";
            }
            Rutina actual = existente.get();
            actual.setNombre(rutina.getNombre());
            actual.setDescripcion(rutina.getDescripcion());
            actual.setNivel(rutina.getNivel());
            rutina = actual;
        }
        try {
            rutinaService.guardar(rutina);
        } catch (DataIntegrityViolationException ex) {
            result.reject("database", "No se pudo guardar. Revisa los datos.");
            model.addAttribute("niveles", NIVELES);
            return "rutinas/form";
        }
        redirect.addFlashAttribute("mensaje", "Rutina guardada correctamente");
        return "redirect:/rutinas";
    }

    // Solo muestra la confirmación, no borra nada
    @GetMapping("/borrar/{id}")
    public String confirmarBorrado(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Optional<Rutina> rutina = rutinaService.buscarConEjercicios(id);
        if (rutina.isEmpty()) {
            redirect.addFlashAttribute("error", "La rutina no existe");
            return "redirect:/rutinas";
        }
        model.addAttribute("rutina", rutina.get());
        return "rutinas/confirmar-borrado";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id, RedirectAttributes redirect) {
        if (rutinaService.eliminar(id)) {
            redirect.addFlashAttribute("mensaje", "Rutina y sus ejercicios eliminados");
        } else {
            redirect.addFlashAttribute("error", "La rutina no existe");
        }
        return "redirect:/rutinas";
    }
}