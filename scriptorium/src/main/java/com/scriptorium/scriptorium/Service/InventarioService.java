package com.scriptorium.scriptorium.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import static com.scriptorium.scriptorium.Service.HelperError.GENERO_NO_ENCONTRADO;
import static com.scriptorium.scriptorium.Service.HelperError.LIBRO_NO_ENCONTRADO;

import com.scriptorium.scriptorium.domain.Inventario;
import com.scriptorium.scriptorium.domain.Libro;
import com.scriptorium.scriptorium.infrastructure.repositories.InventarioRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.LibroRepository;
import com.scriptorium.scriptorium.dto.InventarioRequestDTO;
import com.scriptorium.scriptorium.dto.InventarioResponseDTO;

@Service
public class InventarioService {

    private final InventarioRepository repo;
    private final LibroRepository libroRepository;

    public InventarioService(InventarioRepository repo, LibroRepository libroRepository) {
        this.repo = repo;
        this.libroRepository = libroRepository;
    }

    public List<InventarioResponseDTO> listar() {
        return repo.findAll().stream()
                .map(b -> new InventarioResponseDTO(b.getIdInventario(), b.getStock(), b.getLibro().getIdLibro()))
                .collect(Collectors.toList());
    }

    public InventarioResponseDTO guardar(InventarioRequestDTO dto) {
        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RuntimeException(LIBRO_NO_ENCONTRADO));

        Inventario nuevo = new Inventario();
        nuevo.setStock(dto.getStock());
        nuevo.setLibro(libro);
        Inventario guardado = repo.save(nuevo);
        return new InventarioResponseDTO(guardado.getIdInventario(), guardado.getStock(),
                guardado.getLibro().getIdLibro());
    }

    public Optional<InventarioResponseDTO> obtenerPorId(Long id) {
        return repo.findById(id)
                .map(b -> new InventarioResponseDTO(
                        b.getIdInventario(),
                        b.getStock(),
                        b.getLibro() != null ? b.getLibro().getIdLibro() : 0 // En caso de que el genero sea null, se
                                                                             // devuelve 0.
                ));
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<InventarioResponseDTO> actualizar(Long id, InventarioRequestDTO dto) {
        return repo.findById(id)
                .map(b -> {
                    Libro libro = libroRepository.findById(dto.getLibroId())
                            .orElseThrow(() -> new RuntimeException(GENERO_NO_ENCONTRADO));

                    b.setStock(dto.getStock());
                    b.setLibro(libro);

                    Inventario actualizado = repo.save(b);

                    return new InventarioResponseDTO(
                            actualizado.getIdInventario(),
                            actualizado.getStock(),
                            actualizado.getLibro() != null ? actualizado.getLibro().getIdLibro() : 0);
                });
    }
}
