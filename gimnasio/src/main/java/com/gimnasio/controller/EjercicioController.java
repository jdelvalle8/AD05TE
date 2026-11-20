package com.gimnasio.controller;

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
import com.gimnasio.model.Ejercicio;
import com.gimnasio.service.EjercicioService;
import com.gimnasio.service.RutinaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ejercicios")
public class EjercicioController {

    private final EjercicioService ejercicioService;
    private final RutinaService rutinaService;

    public EjercicioController(EjercicioService ejercicioService, RutinaService rutinaService) {
        this.ejercicioService = ejercicioService;
        this.rutinaService = rutinaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ejercicios", ejercicioService.listar());
        return "ejercicios/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model, RedirectAttributes redirect) {
        if (rutinaService.listar().isEmpty()) {
            redirect.addFlashAttribute("error", "Primero tienes que crear una rutina");
            return "redirect:/rutinas";
        }
        model.addAttribute("ejercicio", new Ejercicio());
        model.addAttribute("rutinas", rutinaService.listar());
        return "ejercicios/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Optional<Ejercicio> ejercicio = ejercicioService.buscarPorId(id);
        if (ejercicio.isEmpty()) {
            redirect.addFlashAttribute("error", "El ejercicio no existe");
            return "redirect:/ejercicios";
        }
        model.addAttribute("ejercicio", ejercicio.get());
        model.addAttribute("rutinas", rutinaService.listar());
        return "ejercicios/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("ejercicio") Ejercicio ejercicio, BindingResult result,
            Model model, RedirectAttributes redirect) {
        if (!result.hasErrors()) {
            // Si trae id, tiene que existir para actualizar esa fila
            if (ejercicio.getId() != null && ejercicioService.buscarPorId(ejercicio.getId()).isEmpty()) {
                redirect.addFlashAttribute("error", "El ejercicio no existe");
                return "redirect:/ejercicios";
            }
            try {
                if (!ejercicioService.guardar(ejercicio)) {
                    result.rejectValue("rutina", "rutina.invalida", "La rutina elegida no existe");
                }
            } catch (DataIntegrityViolationException ex) {
                result.reject("database", "No se pudo guardar. Revisa los datos.");
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("rutinas", rutinaService.listar());
            return "ejercicios/form";
        }
        redirect.addFlashAttribute("mensaje", "Ejercicio guardado correctamente");
        return "redirect:/ejercicios";
    }

    // Solo muestra la confirmación, no borra nada
    @GetMapping("/borrar/{id}")
    public String confirmarBorrado(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Optional<Ejercicio> ejercicio = ejercicioService.buscarPorId(id);
        if (ejercicio.isEmpty()) {
            redirect.addFlashAttribute("error", "El ejercicio no existe");
            return "redirect:/ejercicios";
        }
        model.addAttribute("ejercicio", ejercicio.get());
        return "ejercicios/confirmar-borrado";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id, RedirectAttributes redirect) {
        if (ejercicioService.eliminar(id)) {
            redirect.addFlashAttribute("mensaje", "Ejercicio eliminado");
        } else {
            redirect.addFlashAttribute("error", "El ejercicio no existe");
        }
        return "redirect:/ejercicios";
    }
}