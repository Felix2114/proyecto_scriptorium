package com.scriptorium.scriptorium.infrastructure.repositories;

import com.scriptorium.scriptorium.domain.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u " +
            "WHERE LOWER(u.nombre) LIKE LOWER(CONCAT('%', :palabra, '%')) " +
            "OR LOWER(u.direccion) LIKE LOWER(CONCAT('%', :palabra, '%')) " +
            "OR LOWER(u.contacto) LIKE LOWER(CONCAT('%', :palabra, '%'))")
    List<Usuario> buscarUsuarios(@Param("palabra") String palabra);

    Optional<Usuario> findByNombre(String nombre);

}
