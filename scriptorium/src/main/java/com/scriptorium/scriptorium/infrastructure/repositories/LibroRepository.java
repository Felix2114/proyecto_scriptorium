package com.scriptorium.scriptorium.infrastructure.repositories;

import com.scriptorium.scriptorium.domain.Libro;
import com.scriptorium.scriptorium.dto.LibroResponseDTO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query(value = "SELECT * FROM buscar_libros(:palabra)", nativeQuery = true)
    List<LibroResponseDTO> buscarLibros(@Param("palabra") String palabra);
}
