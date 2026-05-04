package com.scriptorium.scriptorium.infrastructure.repositories;

import com.scriptorium.scriptorium.domain.Libro;
import com.scriptorium.scriptorium.dto.LibroResponseDTO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT new com.scriptorium.scriptorium.dto.LibroResponseDTO(l.idLibro, l.titulo, l.autor, l.isbn, l.precio, l.genero.idGenero) " +
           "FROM Libro l " +
           "WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :palabra, '%')) " +
           "   OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :palabra, '%')) " +
           "   OR LOWER(l.isbn) LIKE LOWER(CONCAT('%', :palabra, '%'))")
    List<LibroResponseDTO> buscarLibros(@Param("palabra") String palabra);
}
