package com.scriptorium.scriptorium.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scriptorium.scriptorium.domain.Multa;
import com.scriptorium.scriptorium.domain.Prestamo;
import com.scriptorium.scriptorium.domain.TipoMulta;
import com.scriptorium.scriptorium.infrastructure.repositories.MultaRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.PrestamoRepository;
import com.scriptorium.scriptorium.infrastructure.repositories.TipoMultaRepository;
import com.scriptorium.scriptorium.dto.MultaRequestDTO;
import com.scriptorium.scriptorium.dto.MultaResponseDTO;

import static com.scriptorium.scriptorium.Service.HelperError.PRESTAMO_NO_ENCONTRADO;

import static com.scriptorium.scriptorium.Service.HelperError.TIPO_MULTA_NO_ENCONTRADO;;

@Service
public class MultaService {

        private final MultaRepository multaRepo;
        private final PrestamoRepository prestamoRepo;
        private final TipoMultaRepository tipoMultaRepo;

        public MultaService(MultaRepository multaRepo, PrestamoRepository prestamoRepo,
                        TipoMultaRepository tipoMultaRepo) {
                this.multaRepo = multaRepo;
                this.prestamoRepo = prestamoRepo;
                this.tipoMultaRepo = tipoMultaRepo;
        }

        public List<MultaResponseDTO> listar() {
                return multaRepo.findAll().stream()
                                .map(p -> new MultaResponseDTO(
                                                p.getIdMulta(),
                                                p.getMonto(),
                                                p.getFechaMulta(),
                                                p.getPrestamo() != null ? p.getPrestamo().getIdPrestamo() : 0,
                                                p.getTipoMulta() != null ? p.getTipoMulta().getIdTipoMulta() : 0))
                                .collect(Collectors.toList());
        }

        public MultaResponseDTO guardar(MultaRequestDTO dto) {
                Prestamo prestamo = prestamoRepo.findById(dto.getPrestamoId())
                                .orElseThrow(() -> new RuntimeException(PRESTAMO_NO_ENCONTRADO));
                TipoMulta tipoMulta = tipoMultaRepo.findById(dto.getTipoMultaId())
                                .orElseThrow(() -> new RuntimeException(TIPO_MULTA_NO_ENCONTRADO));

                Multa nuevo = new Multa();
                nuevo.setMonto(dto.getMonto());
                nuevo.setFechaMulta(dto.getFechaMulta());
                nuevo.setPrestamo(prestamo);
                nuevo.setTipoMulta(tipoMulta);

                Multa guardado = multaRepo.save(nuevo);

                return new MultaResponseDTO(
                                guardado.getIdMulta(),
                                guardado.getMonto(),
                                guardado.getFechaMulta(),
                                guardado.getPrestamo() != null ? guardado.getPrestamo().getIdPrestamo() : 0,
                                guardado.getTipoMulta() != null ? guardado.getTipoMulta().getIdTipoMulta() : 0);
        }

        public Optional<MultaResponseDTO> obtenerPorId(Long id) {
                return multaRepo.findById(id)
                                .map(p -> new MultaResponseDTO(
                                                p.getIdMulta(),
                                                p.getMonto(),
                                                p.getFechaMulta(),
                                                p.getPrestamo() != null ? p.getPrestamo().getIdPrestamo() : 0,
                                                p.getTipoMulta() != null ? p.getTipoMulta().getIdTipoMulta() : 0));
        }

        public boolean eliminar(Long id) {
                if (prestamoRepo.existsById(id)) {
                        prestamoRepo.deleteById(id);
                        return true;
                }
                return false;
        }

        public Optional<MultaResponseDTO> actualizar(Long id, MultaRequestDTO dto) {
                return multaRepo.findById(id)
                                .map(p -> {
                                        Prestamo prestamo = prestamoRepo.findById(dto.getPrestamoId())
                                                        .orElseThrow(() -> new RuntimeException(
                                                                        PRESTAMO_NO_ENCONTRADO));

                                        TipoMulta tipoMulta = tipoMultaRepo.findById(dto.getTipoMultaId())
                                                        .orElseThrow(() -> new RuntimeException(
                                                                        TIPO_MULTA_NO_ENCONTRADO));

                                        p.setMonto(dto.getMonto());
                                        p.setFechaMulta(dto.getFechaMulta());
                                        p.setPrestamo(prestamo);
                                        p.setTipoMulta(tipoMulta);

                                        Multa actualizado = multaRepo.save(p);

                                        return new MultaResponseDTO(
                                                        actualizado.getIdMulta(),
                                                        actualizado.getMonto(),
                                                        actualizado.getFechaMulta(),
                                                        actualizado.getPrestamo() != null
                                                                        ? actualizado.getPrestamo().getIdPrestamo()
                                                                        : 0,
                                                        actualizado.getTipoMulta() != null
                                                                        ? actualizado.getTipoMulta().getIdTipoMulta()
                                                                        : 0);
                                });
        }
}
