package com.scriptorium.scriptorium.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scriptorium.scriptorium.domain.Genero;
import com.scriptorium.scriptorium.domain.Inventario;
import com.scriptorium.scriptorium.domain.Libro;
import com.scriptorium.scriptorium.infrastructure.repositories.GeneroRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.InventarioRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.LibroRepository;
import com.scriptorium.scriptorium.dto.LibroRequestDTO;
import com.scriptorium.scriptorium.dto.LibroResponseDTO;

import static com.scriptorium.scriptorium.Service.HelperError.GENERO_NO_ENCONTRADO;

@Service
public class LibroService {

    private final LibroRepository repo;
    private final GeneroRepository generoRepository;
    private final InventarioRepository inventarioRepository;

    public LibroService(LibroRepository repo, GeneroRepository generoRepository,
            InventarioRepository inventarioRepository) {
        this.repo = repo;
        this.generoRepository = generoRepository;
        this.inventarioRepository = inventarioRepository;
    }

    public List<LibroResponseDTO> listar() {
        return repo.findAll().stream()
                .map(b -> new LibroResponseDTO(b.getIdLibro(), b.getTitulo(), b.getAutor(), b.getIsbn(), b.getPrecio(),
                        b.getGenero().getIdGenero()))
                .collect(Collectors.toList());
    }

    public List<LibroResponseDTO> buscar(String palabra) {
        return repo.buscarLibros(palabra);
    }

    public LibroResponseDTO guardar(LibroRequestDTO dto) {
        Genero genero = generoRepository.findById(dto.getGeneroId())
                .orElseThrow(() -> new RuntimeException(GENERO_NO_ENCONTRADO));

        Libro nuevo = new Libro();
        nuevo.setTitulo(dto.getTitulo());
        nuevo.setAutor(dto.getAutor());
        nuevo.setIsbn(dto.getIsbn());
        nuevo.setGenero(genero);
        nuevo.setPrecio(dto.getPrecio());
        Libro guardado = repo.save(nuevo);

        Inventario inventario = new Inventario();
        inventario.setLibro(guardado);
        inventario.setStock(dto.getStock());
        inventarioRepository.save(inventario);

        return new LibroResponseDTO(guardado.getIdLibro(), guardado.getTitulo(), guardado.getAutor(),
                guardado.getIsbn(), guardado.getPrecio(), guardado.getGenero().getIdGenero());
    }

    public Optional<LibroResponseDTO> obtenerPorId(Long id) {
        return repo.findById(id)
                .map(b -> new LibroResponseDTO(
                        b.getIdLibro(),
                        b.getTitulo(),
                        b.getAutor(),
                        b.getIsbn(),
                        b.getPrecio(),
                        b.getGenero() != null ? b.getGenero().getIdGenero() : 0 // En caso de que el genero sea null, se
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

    public Optional<LibroResponseDTO> actualizar(Long id, LibroRequestDTO dto) {
        return repo.findById(id)
                .map(b -> {
                    Genero genero = generoRepository.findById(dto.getGeneroId())
                            .orElseThrow(() -> new RuntimeException(GENERO_NO_ENCONTRADO));

                    b.setTitulo(dto.getTitulo());
                    b.setAutor(dto.getAutor());
                    b.setIsbn(dto.getIsbn());
                    b.setGenero(genero);
                    b.setPrecio(dto.getPrecio());

                    Libro actualizado = repo.save(b);

                    return new LibroResponseDTO(
                            actualizado.getIdLibro(),
                            actualizado.getTitulo(),
                            actualizado.getAutor(),
                            actualizado.getIsbn(),
                            actualizado.getPrecio(),
                            actualizado.getGenero() != null ? actualizado.getGenero().getIdGenero() : 0);
                });
    }
}
