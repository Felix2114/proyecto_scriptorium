package com.scriptorium.scriptorium.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.scriptorium.scriptorium.domain.Prestamo;
import com.scriptorium.scriptorium.domain.Usuario;
import com.scriptorium.scriptorium.domain.Libro;
import com.scriptorium.scriptorium.domain.Bibliotecario;
import com.scriptorium.scriptorium.domain.Inventario;
import com.scriptorium.scriptorium.infrastructure.repositories.PrestamoRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.UsuarioRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.LibroRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.BibliotecarioRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.InventarioRepository;
import com.scriptorium.scriptorium.dto.MultaRequestDTO;
import com.scriptorium.scriptorium.dto.PrestamoRequestDTO;
import com.scriptorium.scriptorium.dto.PrestamoResponseDTO;
import static com.scriptorium.scriptorium.Service.HelperError.*;

@Service
public class PrestamoService {

        @Autowired
        private MultaService multaService;
        private final PrestamoRepository prestamoRepo;
        private final UsuarioRepository usuarioRepo;
        private final LibroRepository libroRepo;
        private final BibliotecarioRepository bibliotecarioRepo;
        private final InventarioRepository inventarioRepo;
        private final JdbcTemplate jdbcTemplate;

        public PrestamoService(PrestamoRepository prestamoRepo, UsuarioRepository usuarioRepo,
                        LibroRepository libroRepo, BibliotecarioRepository bibliotecarioRepo,
                        InventarioRepository inventarioRepo, JdbcTemplate jdbcTemplate) {
                this.prestamoRepo = prestamoRepo;
                this.usuarioRepo = usuarioRepo;
                this.libroRepo = libroRepo;
                this.bibliotecarioRepo = bibliotecarioRepo;
                this.inventarioRepo = inventarioRepo;
                this.jdbcTemplate = jdbcTemplate;
        }

        public List<PrestamoResponseDTO> buscarPrestamos(String palabra) {
                List<Object[]> resultados = prestamoRepo.buscarPrestamosRaw(palabra);
                return resultados.stream().map(row -> new PrestamoResponseDTO(
                                ((Number) row[0]).longValue(),
                                (String) row[1],
                                ((Number) row[2]).longValue(),
                                ((Number) row[3]).longValue(),
                                ((Number) row[4]).longValue(),
                                (Boolean) row[5],
                                (Boolean) row[6],
                                (Boolean) row[7],
                                (String) row[8],
                                (String) row[9],
                                ((java.sql.Date) row[10]).toLocalDate(),
                                ((java.sql.Date) row[11]).toLocalDate())).collect(Collectors.toList());
        }

        public List<PrestamoResponseDTO> listar() {
                return prestamoRepo.findAll().stream()
                                .map(p -> new PrestamoResponseDTO(
                                                p.getIdPrestamo(),
                                                p.getFicha(),
                                                p.getUsuario() != null ? p.getUsuario().getIdUsuario() : 0,
                                                p.getLibro() != null ? p.getLibro().getIdLibro() : 0,
                                                p.getBibliotecario() != null ? p.getBibliotecario().getIdBibliotecario()
                                                                : 0,
                                                p.isActivo(),
                                                p.isMultado(),
                                                p.isDevuelto(),
                                                p.getEstadoPrestamo(),
                                                p.getEstadoDevuelto(),
                                                p.getFechaInicio(),
                                                p.getFechaFin()))
                                .collect(Collectors.toList());
        }

        public PrestamoResponseDTO guardar(PrestamoRequestDTO dto) {
                System.out.println(dto.getUsuario());
                Usuario usuario = usuarioRepo.findByNombre(dto.getUsuario())
                                .orElseThrow(() -> new RuntimeException(USUARIO_NO_ENCONTRADO));

                Libro libro = libroRepo.findById(dto.getLibroId())
                                .orElseThrow(() -> new RuntimeException(LIBRO_NO_ENCONTRADO));

                Bibliotecario bibliotecario = bibliotecarioRepo.findByUsuario(dto.getBibliotecarioId())
                                .orElseThrow(() -> new RuntimeException(BIBLIOTECARIO_NO_ENCONTRADO));

                Inventario inventario = inventarioRepo.findByLibro(libro)
                                .orElseThrow(() -> new RuntimeException(INVENTARIO_NO_ENCONTRADO));

                if (inventario.getStock() <= 0) {
                        throw new RuntimeException(SIN_EJEMPLARES);
                }

                inventario.setStock(inventario.getStock() - 1);
                inventarioRepo.save(inventario);

                Prestamo nuevo = new Prestamo();
                nuevo.setFicha(dto.getFicha());
                nuevo.setUsuario(usuario);
                nuevo.setLibro(libro);
                nuevo.setBibliotecario(bibliotecario);
                nuevo.setActivo(dto.isActivo());
                nuevo.setMultado(dto.isMultado());
                nuevo.setEstadoPrestamo(dto.getEstadoPrestamo());
                nuevo.setEstadoDevuelto("");
                nuevo.setFechaInicio(dto.getFechaInicio());
                nuevo.setFechaFin(dto.getFechaInicio().plusDays(15));
                nuevo.setDevuelto(false);

                Prestamo guardado = prestamoRepo.save(nuevo);

                return new PrestamoResponseDTO(
                                guardado.getIdPrestamo(),
                                guardado.getFicha(),
                                guardado.getUsuario() != null ? guardado.getUsuario().getIdUsuario() : 0,
                                guardado.getLibro() != null ? guardado.getLibro().getIdLibro() : 0,
                                guardado.getBibliotecario() != null ? guardado.getBibliotecario().getIdBibliotecario()
                                                : 0,
                                guardado.isActivo(),
                                guardado.isMultado(),
                                guardado.isDevuelto(),
                                guardado.getEstadoPrestamo(),
                                guardado.getEstadoDevuelto(),
                                guardado.getFechaInicio(),
                                guardado.getFechaFin());
        }

        public Optional<PrestamoResponseDTO> obtenerPorId(Long id) {
                return prestamoRepo.findById(id)
                                .map(p -> new PrestamoResponseDTO(
                                                p.getIdPrestamo(),
                                                p.getFicha(),
                                                p.getUsuario() != null ? p.getUsuario().getIdUsuario() : 0,
                                                p.getLibro() != null ? p.getLibro().getIdLibro() : 0,
                                                p.getBibliotecario() != null ? p.getBibliotecario().getIdBibliotecario()
                                                                : 0,
                                                p.isActivo(),
                                                p.isMultado(),
                                                p.isDevuelto(),
                                                p.getEstadoPrestamo(),
                                                p.getEstadoDevuelto(),
                                                p.getFechaInicio(),
                                                p.getFechaFin()));
        }

        public boolean eliminar(Long id) {
                if (prestamoRepo.existsById(id)) {
                        prestamoRepo.deleteById(id);
                        return true;
                }
                return false;
        }

        public Optional<PrestamoResponseDTO> actualizar(Long id, PrestamoRequestDTO dto) {
                return prestamoRepo.findById(id)
                                .map(p -> {
                                        Usuario usuario = usuarioRepo.findByNombre(dto.getUsuario())
                                                        .orElseThrow(() -> new RuntimeException(USUARIO_NO_ENCONTRADO));

                                        Libro libro = libroRepo.findById(dto.getLibroId())
                                                        .orElseThrow(() -> new RuntimeException(LIBRO_NO_ENCONTRADO));

                                        Bibliotecario bibliotecario = bibliotecarioRepo
                                                        .findByUsuario(dto.getBibliotecarioId())
                                                        .orElseThrow(() -> new RuntimeException(
                                                                        BIBLIOTECARIO_NO_ENCONTRADO));

                                        p.setFicha(dto.getFicha());
                                        p.setUsuario(usuario);
                                        p.setLibro(libro);
                                        p.setBibliotecario(bibliotecario);
                                        p.setActivo(dto.isActivo());
                                        p.setMultado(dto.isMultado());
                                        p.setDevuelto(dto.isDevuelto());
                                        p.setEstadoPrestamo(dto.getEstadoPrestamo());
                                        p.setFechaInicio(dto.getFechaInicio());

                                        Prestamo actualizado = prestamoRepo.save(p);

                                        return new PrestamoResponseDTO(
                                                        actualizado.getIdPrestamo(),
                                                        actualizado.getFicha(),
                                                        actualizado.getUsuario() != null
                                                                        ? actualizado.getUsuario().getIdUsuario()
                                                                        : 0,
                                                        actualizado.getLibro() != null
                                                                        ? actualizado.getLibro().getIdLibro()
                                                                        : 0,
                                                        actualizado.getBibliotecario() != null
                                                                        ? actualizado.getBibliotecario()
                                                                                        .getIdBibliotecario()
                                                                        : 0,
                                                        actualizado.isActivo(),
                                                        actualizado.isMultado(),
                                                        actualizado.isDevuelto(),
                                                        actualizado.getEstadoPrestamo(),
                                                        actualizado.getEstadoDevuelto(),
                                                        actualizado.getFechaInicio(),
                                                        actualizado.getFechaFin());
                                });
        }

        public void generarMultaPorEstadoDevuelto(Prestamo prestamo) {
                Libro libro = prestamo.getLibro();
                if (libro == null || libro.getPrecio() == 0) {
                        throw new RuntimeException("No se puede generar multa: libro o precio no disponible.");
                }

                MultaRequestDTO multaDTO = new MultaRequestDTO();
                multaDTO.setTipoMultaId(3); // Tipo 3: Multa por estado devuelto
                multaDTO.setPrestamoId(prestamo.getIdPrestamo());
                multaDTO.setMonto((float) libro.getPrecio());
                multaDTO.setFechaMulta(LocalDate.now());

                multaService.guardar(multaDTO);
                System.out.println("MULTA GENERADA");
        }

        public void marcarComoMultado(Long idPrestamo) {
                Optional<Prestamo> prestamoOpt = prestamoRepo.findById(idPrestamo);
                if (prestamoOpt.isPresent()) {
                        Prestamo prestamo = prestamoOpt.get();
                        prestamo.setMultado(true);
                        prestamoRepo.save(prestamo);
                }
        }

        public PrestamoResponseDTO devolverLibro(Long prestamoId, String estadoD) {
                Prestamo prestamo = prestamoRepo.findById(prestamoId)
                                .orElseThrow(() -> new RuntimeException(PRESTAMO_NO_ENCONTRADO));

                if (prestamo.isDevuelto()) {
                        throw new RuntimeException(YA_DEVUELTO);
                }

                Libro libro = prestamo.getLibro();
                Inventario inventario = inventarioRepo.findByLibro(libro)
                                .orElseThrow(() -> new RuntimeException(INVENTARIO_NO_ENCONTRADO));

                inventario.setStock(inventario.getStock() + 1);
                inventarioRepo.save(inventario);

                prestamo.setEstadoDevuelto(estadoD);

                String estadoPrestado = prestamo.getEstadoPrestamo();
                String estadoDevuelto = prestamo.getEstadoDevuelto();

                int nivelPrestado = EstadoLibroMap.obtenerNivelEstado(estadoPrestado);
                int nivelDevuelto = EstadoLibroMap.obtenerNivelEstado(estadoDevuelto);

                if (nivelDevuelto > nivelPrestado) {
                        prestamo.setMultado(true);
                        try {
                                generarMultaPorEstadoDevuelto(prestamo);
                        } catch (Exception e) {
                                System.out.println("Error al generar multa: " + e.getMessage());
                        }
                } else {
                        prestamo.setDevuelto(true);
                        prestamo.setActivo(false);
                }

                Prestamo actualizado = prestamoRepo.save(prestamo);

                return new PrestamoResponseDTO(
                                actualizado.getIdPrestamo(),
                                actualizado.getFicha(),
                                actualizado.getUsuario() != null ? actualizado.getUsuario().getIdUsuario() : 0,
                                actualizado.getLibro() != null ? actualizado.getLibro().getIdLibro() : 0,
                                actualizado.getBibliotecario() != null
                                                ? actualizado.getBibliotecario().getIdBibliotecario()
                                                : 0,
                                actualizado.isActivo(),
                                actualizado.isMultado(),
                                actualizado.isDevuelto(),
                                actualizado.getEstadoPrestamo(),
                                actualizado.getEstadoDevuelto(),
                                actualizado.getFechaInicio(),
                                actualizado.getFechaFin());
        }

        public PrestamoResponseDTO pagarMulta(Long prestamoId) {
                Prestamo prestamo = prestamoRepo.findById(prestamoId)
                                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

                if (!prestamo.isMultado()) {
                        throw new RuntimeException("Este préstamo no tiene multa pendiente");
                }

                // Marcar como pagado
                prestamo.setActivo(false);
                prestamo.setMultado(false);
                prestamo.setDevuelto(true); // por si aún no estaba marcado como devuelto

                Prestamo actualizado = prestamoRepo.save(prestamo);

                return new PrestamoResponseDTO(
                                actualizado.getIdPrestamo(),
                                actualizado.getFicha(),
                                actualizado.getUsuario() != null ? actualizado.getUsuario().getIdUsuario() : 0,
                                actualizado.getLibro() != null ? actualizado.getLibro().getIdLibro() : 0,
                                actualizado.getBibliotecario() != null
                                                ? actualizado.getBibliotecario().getIdBibliotecario()
                                                : 0,
                                actualizado.isActivo(),
                                actualizado.isMultado(),
                                actualizado.isDevuelto(),
                                actualizado.getEstadoPrestamo(),
                                actualizado.getEstadoDevuelto(),
                                actualizado.getFechaInicio(),
                                actualizado.getFechaFin());
        }

        public Optional<BigDecimal> obtenerMontoMulta(int idPrestamo) {
                String sql = "SELECT obtener_monto_multa(?)";

                try {
                        BigDecimal monto = jdbcTemplate.queryForObject(sql, BigDecimal.class, idPrestamo);
                        return Optional.ofNullable(monto);
                } catch (EmptyResultDataAccessException e) {
                        return Optional.empty();
                }
        }

}
