/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

/**
 *
 * @author RENZO
 */



import com.example.demo.entidades.Editorial;
import com.example.demo.errores.ErrorServicio;
import com.example.demo.repositorios.EditorialRepositorio;
import java.util.List;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
 
    @Autowired
    private EditorialRepositorio editorialRepositorio;
 
    @Transactional(propagation = Propagation.NESTED)
    public void cargar (String nombre, Boolean alta) throws ErrorServicio{
 
        validar(nombre, nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(Boolean.TRUE);
 
        editorialRepositorio.save(editorial);
    }
 
    @Transactional(propagation = Propagation.NESTED)
    public void modificar (String nombre, String id) throws ErrorServicio{
 
         Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial =respuesta.get();
            editorial.setNombre(nombre);
 
            editorialRepositorio.save(editorial);
        } else{
            throw new ErrorServicio("No hay una Editorial con ese Id");
        }
    }
 
    @Transactional(propagation = Propagation.NESTED)
    public void eliminar (String id) throws ErrorServicio{
 
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
           Editorial editorial =respuesta.get();
           editorial.setAlta(Boolean.FALSE);
 
           editorialRepositorio.save(editorial);
        } else{
            throw new ErrorServicio("No hay una Editorial con ese Id");
        }
    }
    
    @Transactional(propagation = Propagation.NESTED)
    public void darAlta (String id) throws ErrorServicio{
 
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
           Editorial editorial =respuesta.get();
           editorial.setAlta(Boolean.TRUE);
 
           editorialRepositorio.save(editorial);
        } else{
            throw new ErrorServicio("No hay una Editorial con ese Id");
        }
    }
 
    public void consultar (String id) throws ErrorServicio{
 
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
           Editorial editorial =respuesta.get();
 
        } else{
            throw new ErrorServicio("No hay una Editorial con ese Id");
        }
    }
 
     public void validar(String nombre, String id) throws ErrorServicio {
 
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("La Editorial debe tener un nombre");
        }
        if (id == null) {
            throw new ErrorServicio(" La Editorial debe tener un Id");
        }
    }
     public List<Editorial> listadoEditorial() {
        return editorialRepositorio.findAll();
    }

    public Editorial buscarPorNombre(String nombre) {
        return editorialRepositorio.buscarPorNombre(nombre);
    }

    public Editorial buscarPorId(String id) {
        return editorialRepositorio.buscarPorId(id) ;
    }
}