package com.scriptorium.scriptorium.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scriptorium.scriptorium.Service.MultaService;
import com.scriptorium.scriptorium.dto.MultaRequestDTO;
import com.scriptorium.scriptorium.dto.MultaResponseDTO;
import com.scriptorium.scriptorium.utils.ApiResponse;

@RestController
@RequestMapping("/Multa")
public class MultaControl {

    private final MultaService service;

    public MultaControl(MultaService service) {
        this.service = service;
    }

     @GetMapping
    public ResponseEntity<ApiResponse<List<MultaResponseDTO>>> listar() {
        List<MultaResponseDTO> lista = service.listar();
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Lista de multas obtenida", lista)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MultaResponseDTO>> guardar(@RequestBody MultaRequestDTO dto) {
        MultaResponseDTO multa = service.guardar(dto);
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.CREATED.value(), "Multa creada exitosamente", multa),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MultaResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(multa -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Multa encontrada", multa)
                ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Multa no encontrada", null)
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Multa eliminada", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Multa no encontrada", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MultaResponseDTO>> actualizar(@PathVariable Long id,
                                                                    @RequestBody MultaRequestDTO dto) {
        return service.actualizar(id, dto)
                .map(actualizado -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Multa actualizada", actualizado)
                ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Multa no encontrada", null)
                ));
    }
}
