package com.example.demo.servicios;

import com.example.demo.entidades.Autor;
import com.example.demo.entidades.Editorial;
import com.example.demo.entidades.Libro;
import com.example.demo.errores.ErrorServicio;
import com.example.demo.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional(propagation = Propagation.NESTED)
    public void crearLibro(String ISBN, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws ErrorServicio {

        validar(ISBN, titulo, anio, ejemplares, autor, editorial);
 
        Libro libro = new Libro();
        libro.setISBN(ISBN);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresRestantes(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setAlta(true);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

 @Transactional(propagation = Propagation.NESTED)
    public void validar(String ISBN, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws ErrorServicio {

        if (ISBN == null || ISBN.isEmpty()) {
            throw new ErrorServicio("El ISBN no puede ser nulo");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El titulo no puede ser nulo");
        }

        if (anio == null || anio.toString().length() > 4) {
            throw new ErrorServicio("El año no puede ser nulo o mayor de 4 dígitos");
        }

        if (autor == null) {
            throw new ErrorServicio("El autor no puede ser nulo");
        }

        if (editorial == null) {
            throw new ErrorServicio("El editorial no puede ser nulo");
        }

    }

    @Transactional
    public void modificarLibro(String id, String ISBN, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws ErrorServicio {
        validar(ISBN, titulo, anio, ejemplares, autor, editorial);
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setISBN(ISBN);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(0);
            libro.setEjemplaresRestantes(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontró el libro pedido.");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void darDeAlta(String id) throws ErrorServicio {
        Optional<Libro> libroABuscar = libroRepositorio.findById(id);
        if (libroABuscar.isPresent()) {
            Libro libro = libroABuscar.get();
            libro.setAlta(true);

            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("El libro no ha sido encontrado.");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void darDeBaja(String id) throws ErrorServicio {
        Optional<Libro> libroABuscar = libroRepositorio.findById(id);
        if (libroABuscar.isPresent()) {
            Libro libro = libroABuscar.get();
            libro.setAlta(false);

            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("El libro no ha sido encontrado.");
        }

    }

    public List<Libro> listadoLibros() {
        return (List<Libro>) libroRepositorio.findAll();
    }

    public Libro buscaLibros(String id) {
        return  libroRepositorio.buscarPorId(id);
    }

    public Libro buscaLibrosPorTitulo(String titulo) {
        return libroRepositorio.buscarPorTitulo(titulo);
    }
    public List <Libro> buscaLibrosPorNombreAutor(String titulo) {
        return libroRepositorio.buscarPorNombreAutor(titulo);
    }
    public List <Libro> buscaLibrosPorIdAutor(String titulo) {
        return libroRepositorio.buscarPorIdAutor(titulo);
    }
    public List <Libro> buscaLibrosPorNombreEditorial(String titulo) {
        return libroRepositorio.buscarPorNombreEditorial(titulo);
    }
    public List <Libro> buscaLibrosPorIdEditorial(String titulo) {
        return libroRepositorio.buscarPorIdEditorial(titulo);
    }
    

    public void crearLibro(String titulo, Integer anio, Integer ejemplares, String autor, String editorial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void modificarLibro(String id, String id0, String titulo, Integer anio, Integer ejemplares, String autor, String editorial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
