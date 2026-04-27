package com.scriptorium.scriptorium.Service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scriptorium.scriptorium.domain.Bibliotecario;
import com.scriptorium.scriptorium.infrastructure.repositories.BibliotecarioRepository;
import com.scriptorium.scriptorium.dto.BibliotecarioRequestDTO;
import com.scriptorium.scriptorium.dto.BibliotecarioResponseDTO;
import static com.scriptorium.scriptorium.Service.HelperError.USUARIO_NOMBRE_EN_USO;

@Service
public class BibliotecarioService {

    private final BibliotecarioRepository repo;

    public BibliotecarioService(BibliotecarioRepository repo) {
        this.repo = repo;
    }

    public Page<BibliotecarioResponseDTO> listar(Pageable pageable) {
        return repo.findAll(pageable)
                .map(b -> new BibliotecarioResponseDTO(b.getIdBibliotecario(), b.getUsuario()));
    }

    public BibliotecarioResponseDTO guardar(BibliotecarioRequestDTO dto) {

        if (verificarNombre(dto.getUsuario())) {
            throw new IllegalArgumentException(USUARIO_NOMBRE_EN_USO);
        }

        Bibliotecario nuevo = new Bibliotecario();
        nuevo.setUsuario(dto.getUsuario());
        nuevo.setContraseña(dto.getContraseña());
        Bibliotecario guardado = repo.save(nuevo);
        return new BibliotecarioResponseDTO(guardado.getIdBibliotecario(), guardado.getUsuario());
    }

    public Optional<BibliotecarioResponseDTO> obtenerPorId(Long id) {
        return repo.findById(id)
                .map(b -> new BibliotecarioResponseDTO(b.getIdBibliotecario(), b.getUsuario()));
    }

    public boolean verificarNombre(String usuario) {
        return repo.findByUsuario(usuario).isPresent();
    }

    public Optional<BibliotecarioResponseDTO> login(String usuario, String contraseña) {
        return repo.findByUsuarioYContrasena(usuario, contraseña)
                .map(b -> new BibliotecarioResponseDTO(b.getIdBibliotecario(), b.getUsuario()));
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<BibliotecarioResponseDTO> actualizar(Long id, BibliotecarioRequestDTO dto) {
        return repo.findById(id)
                .map(b -> {
                    b.setUsuario(dto.getUsuario());
                    b.setContraseña(dto.getContraseña());
                    Bibliotecario actualizado = repo.save(b);
                    return new BibliotecarioResponseDTO(actualizado.getIdBibliotecario(), actualizado.getUsuario());
                });
    }

}
