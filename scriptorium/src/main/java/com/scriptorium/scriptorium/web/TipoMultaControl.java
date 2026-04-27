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

import com.scriptorium.scriptorium.Service.TipoMultaService;
import com.scriptorium.scriptorium.dto.TipoMultaRequestDTO;
import com.scriptorium.scriptorium.dto.TipoMultaResponseDTO;
import com.scriptorium.scriptorium.utils.ApiResponse;

@RestController
@RequestMapping("/TipoMulta")
public class TipoMultaControl {

    private final TipoMultaService service;

    public TipoMultaControl(TipoMultaService service) {
        this.service = service;
    }

        @GetMapping
    public ResponseEntity<ApiResponse<List<TipoMultaResponseDTO>>> listar() {
        List<TipoMultaResponseDTO> lista = service.listar();
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Lista de tipos de multa obtenida", lista)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TipoMultaResponseDTO>> guardar(@RequestBody TipoMultaRequestDTO dto) {
        TipoMultaResponseDTO tipoMulta = service.guardar(dto);
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.CREATED.value(), "Tipo de multa creado exitosamente", tipoMulta),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TipoMultaResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(tipoMulta -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Tipo de multa encontrado", tipoMulta)
                ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Tipo de multa no encontrado", null)
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Tipo de multa eliminado", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Tipo de multa no encontrado", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TipoMultaResponseDTO>> actualizar(@PathVariable Long id,
                                                                        @RequestBody TipoMultaRequestDTO dto) {
        return service.actualizar(id, dto)
                .map(actualizado -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Tipo de multa actualizado", actualizado)
                ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Tipo de multa no encontrado", null)
                ));
    }
}
