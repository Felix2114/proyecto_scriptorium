package com.scriptorium.scriptorium.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scriptorium.scriptorium.domain.Genero;
import com.scriptorium.scriptorium.infrastructure.repositories.GeneroRepository;
import com.scriptorium.scriptorium.dto.GeneroRequestDTO;
import com.scriptorium.scriptorium.dto.GeneroResponseDTO;

@Service
public class GeneroService {

    private final GeneroRepository repo;

    public GeneroService(GeneroRepository repo) {
        this.repo = repo;
    }

    public List<GeneroResponseDTO> listar() {
        return repo.findAll().stream()
                .map(b -> new GeneroResponseDTO(b.getIdGenero(), b.getDescripcion()))
                .collect(Collectors.toList());
    }

    public GeneroResponseDTO guardar(GeneroRequestDTO dto) {
        Genero nuevo = new Genero();
        nuevo.setDescripcion(dto.getDescripcion());
        Genero guardado = repo.save(nuevo);
        return new GeneroResponseDTO(guardado.getIdGenero(), guardado.getDescripcion());
    }

    public Optional<GeneroResponseDTO> obtenerPorId(Long id) {
        return repo.findById(id)
                .map(b -> new GeneroResponseDTO(b.getIdGenero(), b.getDescripcion()));
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<GeneroResponseDTO> actualizar(Long id, GeneroRequestDTO dto) {
        return repo.findById(id)
                .map(b -> {
                    b.setDescripcion(dto.getDescripcion());
                    Genero actualizado = repo.save(b);
                    return new GeneroResponseDTO(actualizado.getIdGenero(), actualizado.getDescripcion());
                });
    }
}
