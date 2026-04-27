package com.scriptorium.scriptorium.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scriptorium.scriptorium.domain.Bibliotecario;
import com.scriptorium.scriptorium.domain.Registro;
import com.scriptorium.scriptorium.infrastructure.repositories.BibliotecarioRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.RegistroRepository;
import com.scriptorium.scriptorium.dto.RegistroRequestDTO;
import com.scriptorium.scriptorium.dto.RegistroResponseDTO;
import static com.scriptorium.scriptorium.Service.HelperError.BIBLIOTECARIO_NO_ENCONTRADO;;

@Service
public class RegistroService {

    private final RegistroRepository repo;
    private final BibliotecarioRepository bibliotecarioRepository;

    public RegistroService(RegistroRepository repo, BibliotecarioRepository bibliotecarioRepository) {
        this.repo = repo;
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    public List<RegistroResponseDTO> listar() {
        return repo.findAll().stream()
                .map(b -> new RegistroResponseDTO(b.getIdRegistro(), b.getDiaRegistro(), b.getHoraRegistro(),
                        b.getBibliotecario().getIdBibliotecario()))
                .collect(Collectors.toList());
    }

    public RegistroResponseDTO guardar(RegistroRequestDTO dto) {
        Bibliotecario bibliotecario = bibliotecarioRepository.findById(dto.getBibliotecarioId())
                .orElseThrow(() -> new RuntimeException(BIBLIOTECARIO_NO_ENCONTRADO));

        Registro nuevo = new Registro();
        nuevo.setDiaRegistro(dto.getDiaRegistro());
        nuevo.setHoraRegistro(dto.getHoraRegistro());
        nuevo.setBibliotecario(bibliotecario);
        Registro guardado = repo.save(nuevo);
        return new RegistroResponseDTO(guardado.getIdRegistro(), guardado.getDiaRegistro(), guardado.getHoraRegistro(),
                guardado.getBibliotecario().getIdBibliotecario());
    }

    public Optional<RegistroResponseDTO> obtenerPorId(Long id) {
        return repo.findById(id)
                .map(b -> new RegistroResponseDTO(
                        b.getIdRegistro(),
                        b.getDiaRegistro(),
                        b.getHoraRegistro(),
                        b.getBibliotecario() != null ? b.getBibliotecario().getIdBibliotecario() : 0 // En caso de que
                                                                                                     // el genero sea
                                                                                                     // null, se
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

    public Optional<RegistroResponseDTO> actualizar(Long id, RegistroRequestDTO dto) {
        return repo.findById(id)
                .map(b -> {
                    Bibliotecario bibliotecario = bibliotecarioRepository.findById(dto.getBibliotecarioId())
                            .orElseThrow(() -> new RuntimeException("Bibliotecario no encontrado"));

                    b.setDiaRegistro(dto.getDiaRegistro());
                    b.setHoraRegistro(dto.getHoraRegistro());
                    b.setBibliotecario(bibliotecario);

                    Registro actualizado = repo.save(b);

                    return new RegistroResponseDTO(
                            actualizado.getIdRegistro(),
                            actualizado.getDiaRegistro(),
                            actualizado.getHoraRegistro(),
                            actualizado.getBibliotecario() != null ? actualizado.getBibliotecario().getIdBibliotecario()
                                    : 0);
                });
    }
}
