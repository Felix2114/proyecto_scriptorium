package com.scriptorium.scriptorium.infrastructure.repositories;

import com.scriptorium.scriptorium.domain.Prestamo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    @Query(value = "SELECT p.id_prestamo, p.ficha, p.usuario_id, p.libro_id, p.bibliotecario_id, p.activo, p.multado, p.devuelto, p.estado_prestamo, p.estado_devuelto, p.fecha_inicio, p.fecha_fin FROM prestamo p JOIN usuario u ON p.usuario_id = u.id_usuario WHERE LOWER(u.nombre) LIKE LOWER(CONCAT('%', :palabra, '%')) OR LOWER(p.ficha) LIKE LOWER(CONCAT('%', :palabra, '%'))", nativeQuery = true)
    List<Object[]> buscarPrestamosRaw(@Param("palabra") String palabra);

}
