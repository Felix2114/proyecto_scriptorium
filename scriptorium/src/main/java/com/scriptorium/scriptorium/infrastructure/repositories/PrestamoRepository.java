package com.scriptorium.scriptorium.infrastructure.repositories;

import com.scriptorium.scriptorium.domain.Prestamo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    @Query(value = "SELECT * FROM buscar_prestamos(:palabra)", nativeQuery = true)
    List<Object[]> buscarPrestamosRaw(@Param("palabra") String palabra);

}
