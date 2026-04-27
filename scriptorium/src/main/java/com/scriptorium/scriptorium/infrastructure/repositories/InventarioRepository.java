package com.scriptorium.scriptorium.infrastructure.repositories;

import com.scriptorium.scriptorium.domain.Inventario;
import com.scriptorium.scriptorium.domain.Libro;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    Optional<Inventario> findByLibro(Libro libro);
    
}
