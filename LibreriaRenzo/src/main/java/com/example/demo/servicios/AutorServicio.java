/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.entidades.Autor;
import com.example.demo.errores.ErrorServicio;
import com.example.demo.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author RENZO
 */
@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;
    
 @Transactional(propagation = Propagation.NESTED)
    public void crearAutor(String nombre) throws ErrorServicio {
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);
        autorRepositorio.save(autor);
    }

    public void validar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede ser nulo.");
        }
    }
 @Transactional(propagation = Propagation.NESTED)
    public void modificar(String id, String nombre, String apellido) throws ErrorServicio {
        Optional<Autor> autorABuscar = autorRepositorio.findById(id);

        if (autorABuscar.isPresent()) {
            Autor autor = autorABuscar.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("El autor no ha sido encontrado.");
        }
    }
 @Transactional(propagation = Propagation.NESTED)
    public void darDeAlta(String id) throws ErrorServicio {
        Optional<Autor> autorABuscar = autorRepositorio.findById(id);
        if (autorABuscar.isPresent()) {
            Autor autor = autorABuscar.get();
            autor.setAlta(true);

            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("El autor no ha sido encontrado.");
        }
    }
 @Transactional(propagation = Propagation.NESTED)
    public void darDeBaja(String id) throws ErrorServicio {
        Optional<Autor> autorABuscar = autorRepositorio.findById(id);
        if (autorABuscar.isPresent()) {
            Autor autor = autorABuscar.get();
            autor.setAlta(false);

            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("El autor no ha sido encontrado.");
        }

    }
    public List<Autor> listadoAutor() {
        return (List<Autor>) autorRepositorio.findAll();
    }

    public Autor buscarPorNombre(String nombre) {
        return autorRepositorio.buscarAutorPorNombre(nombre);
    }

    public Autor buscarPorId(String id) {
        return autorRepositorio.buscarPorId(id);
    }

}
