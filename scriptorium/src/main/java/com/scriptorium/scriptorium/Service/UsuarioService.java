package com.scriptorium.scriptorium.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scriptorium.scriptorium.domain.Usuario;
import com.scriptorium.scriptorium.infrastructure.repositories.UsuarioRepository;
import com.scriptorium.scriptorium.dto.UsuarioRequestDTO;
import com.scriptorium.scriptorium.dto.UsuarioResponseDTO;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<UsuarioResponseDTO> listar() {
        return repo.findAll().stream()
                .map(b -> new UsuarioResponseDTO(b.getIdUsuario(), b.getNombre(), b.getFechaNacimiento(),
                        b.getDireccion(), b.getContacto()))
                .collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> buscarUsuarios(String palabra) {
        List<Object[]> resultados = repo.buscarUsuarios(palabra);

        return resultados.stream().map(obj -> new UsuarioResponseDTO(
                ((Number) obj[0]).longValue(),
                (String) obj[1],
                ((java.sql.Date) obj[2]).toLocalDate(), // pasar a localdate
                (String) obj[3],
                (String) obj[4])).collect(Collectors.toList());
    }

    public UsuarioResponseDTO guardar(UsuarioRequestDTO dto) {
        Usuario nuevo = new Usuario();
        nuevo.setNombre(dto.getNombre());
        nuevo.setFechaNacimiento(dto.getFechaNacimiento());
        nuevo.setDireccion(dto.getDireccion());
        nuevo.setContacto(dto.getContacto());
        Usuario guardado = repo.save(nuevo);
        return new UsuarioResponseDTO(guardado.getIdUsuario(), guardado.getNombre(), guardado.getFechaNacimiento(),
                guardado.getDireccion(), guardado.getContacto());
    }

    public Optional<UsuarioResponseDTO> obtenerPorId(Long id) {
        return repo.findById(id)
                .map(b -> new UsuarioResponseDTO(b.getIdUsuario(), b.getNombre(), b.getFechaNacimiento(),
                        b.getDireccion(), b.getContacto()));
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<UsuarioResponseDTO> actualizar(Long id, UsuarioRequestDTO dto) {
        return repo.findById(id)
                .map(b -> {
                    b.setNombre(dto.getNombre());

                    b.setDireccion(dto.getDireccion());
                    b.setContacto(dto.getContacto());

                    Usuario actualizado = repo.save(b);
                    return new UsuarioResponseDTO(actualizado.getIdUsuario(), actualizado.getNombre(),
                            actualizado.getFechaNacimiento(), actualizado.getDireccion(), actualizado.getContacto());
                });
    }
}
