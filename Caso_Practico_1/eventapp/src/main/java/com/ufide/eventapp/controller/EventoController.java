package com.ufide.eventapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ufide.eventapp.entity.Evento;
import com.ufide.eventapp.service.EventoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService service;

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) String categoria) {
        if (categoria != null && !categoria.isBlank()) {
            model.addAttribute("eventos", service.buscarPorCategoria(categoria));
            model.addAttribute("filtro", "Categoria: " + categoria);
        } else {
            model.addAttribute("eventos", service.listar());
        }
        return "eventos";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Evento evento = service.buscarPorId(id).orElse(null);
        model.addAttribute("evento", evento);
        return "evento";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("evento", new Evento());
        return "eventos/form";
    }

    @PostMapping
    public String crearEvento(@Valid @ModelAttribute Evento evento,
                              BindingResult result,
                              RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "eventos/form";
        }
        service.guardar(evento);
        ra.addFlashAttribute("confirmacion", "El evento fue creado exitosamente");
        return "redirect:/eventos";
    }

    @GetMapping("/{id}/editar")
    public String cargarFormulario(@PathVariable Long id, Model model) {
        Evento evento = service.buscarPorId(id).orElse(null);
        if (evento == null) {
            return "redirect:/eventos";
        }
        model.addAttribute("evento", evento);
        return "eventos/form";
    }

    @PostMapping("/{id}")
    public String modificarEvento(@PathVariable Long id,
                                  @Valid @ModelAttribute Evento evento,
                                  BindingResult result,
                                  RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "eventos/form";
        }
        evento.setId(id);
        service.guardar(evento);
        ra.addFlashAttribute("confirmacion", "Los cambios fueron guardados");
        return "redirect:/eventos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarEvento(@PathVariable Long id, RedirectAttributes ra) {
        service.eliminar(id);
        ra.addFlashAttribute("confirmacion", "El evento fue eliminado del sistema");
        return "redirect:/eventos";
    }

}
