package com.scriptorium.scriptorium.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scriptorium.scriptorium.domain.TipoMulta;
import com.scriptorium.scriptorium.infrastructure.repositories.TipoMultaRepository;
import com.scriptorium.scriptorium.dto.TipoMultaRequestDTO;
import com.scriptorium.scriptorium.dto.TipoMultaResponseDTO;

@Service
public class TipoMultaService {

    private final TipoMultaRepository repo;

    public TipoMultaService(TipoMultaRepository repo) {
        this.repo = repo;
    }

    public List<TipoMultaResponseDTO> listar() {
        return repo.findAll().stream()
                .map(b -> new TipoMultaResponseDTO(b.getIdTipoMulta(), b.getTipo(), b.getDescripcion()))
                .collect(Collectors.toList());
    }

    public TipoMultaResponseDTO guardar(TipoMultaRequestDTO dto) {
        TipoMulta nuevo = new TipoMulta();
        nuevo.setTipo(dto.getTipo());
        nuevo.setDescripcion(dto.getDescripcion());
        TipoMulta guardado = repo.save(nuevo);
        return new TipoMultaResponseDTO(guardado.getIdTipoMulta(), guardado.getTipo(), guardado.getDescripcion());
    }

    public Optional<TipoMultaResponseDTO> obtenerPorId(Long id) {
        return repo.findById(id)
                .map(b -> new TipoMultaResponseDTO(b.getIdTipoMulta(), b.getTipo(), b.getDescripcion()));
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<TipoMultaResponseDTO> actualizar(Long id, TipoMultaRequestDTO dto) {
        return repo.findById(id)
                .map(b -> {
                    b.setTipo(dto.getTipo());
                    b.setDescripcion(dto.getDescripcion());
                    TipoMulta actualizado = repo.save(b);
                    return new TipoMultaResponseDTO(actualizado.getIdTipoMulta(), actualizado.getTipo(), actualizado.getDescripcion());
                });
    }
}
