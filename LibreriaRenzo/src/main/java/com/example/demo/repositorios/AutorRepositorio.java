package com.example.demo.repositorios;

import com.example.demo.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
   @Query("SELECT l FROM Autor l WHERE  l.id = :id")
    public Autor buscarPorId(@Param("id") String id);
    
    @Query("SELECT l FROM Autor l WHERE  l.nombre = :nombre")
    public Autor buscarAutorPorNombre(@Param("nombre") String id);
    


}
