package com.scriptorium.scriptorium.infrastructure.repositories;

import com.scriptorium.scriptorium.domain.Bibliotecario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
    Optional<Bibliotecario> findByUsuario(String usuario);

    @Query("SELECT b FROM Bibliotecario b WHERE b.usuario = :usuario AND b.contrasena = :contrasena")
    Optional<Bibliotecario> findByUsuarioYContrasena(@Param("usuario") String usuario,
            @Param("contrasena") String contrasena);
}
