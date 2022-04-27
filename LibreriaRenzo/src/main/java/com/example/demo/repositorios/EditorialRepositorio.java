package com.example.demo.repositorios;

import com.example.demo.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
    
   @Query("SELECT l FROM Editorial l WHERE  l.id = :id")
    public Editorial buscarPorId(@Param("id") String id);
    
    @Query("SELECT l FROM Editorial l WHERE  l.nombre = :nombre")
    public Editorial buscarPorNombre(@Param("nombre") String id);

    
    
}
