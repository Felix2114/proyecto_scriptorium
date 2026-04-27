package com.scriptorium.scriptorium.web;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scriptorium.scriptorium.Service.PrestamoService;
import com.scriptorium.scriptorium.dto.PrestamoRequestDTO;
import com.scriptorium.scriptorium.dto.PrestamoResponseDTO;
import com.scriptorium.scriptorium.utils.*;

import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Prestamo")
public class PrestamoControl {

    private final PrestamoService service;

    public PrestamoControl(PrestamoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PrestamoResponseDTO>>> listar() {
        List<PrestamoResponseDTO> lista = service.listar();
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Lista de préstamos obtenida", lista));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PrestamoResponseDTO>> guardar(@Valid @RequestBody PrestamoRequestDTO dto) {
        PrestamoResponseDTO a = service.guardar(dto);
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.CREATED.value(), "Préstamo creado exitosamente", a),
                HttpStatus.CREATED);
    }

    @GetMapping("/buscar_prestamo")
    public ResponseEntity<ApiResponse<List<PrestamoResponseDTO>>> buscarPrestamos(@RequestParam String palabra) {
        List<PrestamoResponseDTO> resultados = service.buscarPrestamos(palabra);

        if (resultados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(404, "No se encontraron préstamos", List.of()));
        } else {
            return ResponseEntity.ok(
                    new ApiResponse<>(200, "Préstamos encontrados", resultados));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PrestamoResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(prestamo -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Préstamo encontrado", prestamo)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Préstamo no encontrado", null)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Préstamo eliminado", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "No existe el préstamo", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PrestamoResponseDTO>> actualizar(@PathVariable Long id,
            @Valid @RequestBody PrestamoRequestDTO dto) {
        return service.actualizar(id, dto)
                .map(actualizado -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Préstamo actualizado", actualizado)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "Préstamo no encontrado", null)));
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity<ApiResponse<PrestamoResponseDTO>> devolverLibro(@PathVariable Long id,
            @RequestParam String estadoDevuelto) {
        try {
            PrestamoResponseDTO dto = service.devolverLibro(id, estadoDevuelto);
            return ResponseEntity.ok(
                    new ApiResponse<>(200, "Libro devuelto correctamente", dto));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(400, "Error al devolver el libro: " + ex.getMessage(), null));
        }
    }

    @PutMapping("/pagar-multa/{id}")
    public ResponseEntity<ApiResponse<PrestamoResponseDTO>> pagarMulta(@PathVariable Long id) {
        try {
            PrestamoResponseDTO dto = service.pagarMulta(id);
            return ResponseEntity.ok(
                    new ApiResponse<>(200, "Multa pagada correctamente", dto));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(400, "Error al pagar multa: " + ex.getMessage(), null));
        }
    }

    @PostMapping("/monto-multa")
    public ResponseEntity<?> obtenerMontoMulta(@RequestBody Map<String, Integer> body) {
        Integer idPrestamo = body.get("idPrestamo");

        if (idPrestamo == null) {
            return ResponseEntity.badRequest().body("El campo 'idPrestamo' es obligatorio");
        }

        return service.obtenerMontoMulta(idPrestamo)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok("Este préstamo no tiene multa"));
    }
}
