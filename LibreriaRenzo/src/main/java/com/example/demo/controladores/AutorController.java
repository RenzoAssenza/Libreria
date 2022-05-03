/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Autor;
import com.example.demo.errores.ErrorServicio;
import com.example.demo.servicios.AutorServicio;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AutorController {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/AutorForm")
    public String AutorForm() {
        return "AutorForm.html";
    }

    @PostMapping("/registroAutor")
    public String registroAutor(ModelMap modelo, @RequestParam(required = false) String nombre) {
        try {
            autorServicio.crearAutor(nombre);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "AutorForm.html";
        }
        modelo.put("mensaje", "Autor cargado con éxito");
        return "index.html";
    }

    @GetMapping("/tablasAutor")
    public String listadoAutores(ModelMap modelo) {
        try {
            modelo.addAttribute("lista", autorServicio.listadoAutor());
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }

        return "tablasAutor.html";
    }

    @GetMapping("/autor/{id}")
    public String editarAutor(RedirectAttributes redirectAttributes, @PathVariable("id") String id, ModelMap modelo) {
        try {
            Autor autor = autorServicio.buscarPorId(id);
            modelo.put("tipo", autor);
            modelo.put("nombre", autor.getNombre());
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }

        return "AutorForm.html";
    }

    @PostMapping("/edicionAutor")
    public String editamosAutor(RedirectAttributes redirectAttributes, ModelMap modelo, String id, @RequestParam(required = false) String nombreAutor) {
        Autor autor = autorServicio.buscarPorId(id);
        try {
            modelo.put("nombreAutor", autor.getNombre());
            autorServicio.modificar(id, nombreAutor,id);
        } catch (ErrorServicio ex) {
            modelo.put("tipo", autor);
            modelo.put("error", ex.getMessage());
            redirectAttributes.addAttribute("id", id);
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/autor/{id}";
        }
        modelo.put("mensaje", "Autor modificado con éxito");
        return "index.html";
    }

    @GetMapping("autor/baja/{id}")
    public String bajaAutor(@PathVariable("id") String id, ModelMap modelo) throws ErrorServicio {
        try {
            autorServicio.darDeBaja(id);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "tablasAutor";
        }
        return "redirect:/tablasAutor";
    }

    @GetMapping("autor/alta/{id}")
    public String altaAutor(@PathVariable("id") String id, ModelMap modelo) throws ErrorServicio {
        try {
            autorServicio.darDeAlta(id);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "tablasAutor";
        }

        return "redirect:/tablasAutor";
    }
}
