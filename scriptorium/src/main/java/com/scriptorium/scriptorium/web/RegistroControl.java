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

import com.scriptorium.scriptorium.Service.RegistroService;
import com.scriptorium.scriptorium.dto.RegistroRequestDTO;
import com.scriptorium.scriptorium.dto.RegistroResponseDTO;
import com.scriptorium.scriptorium.utils.ApiResponse;
@RestController
@RequestMapping("/Registro")
public class RegistroControl {

    private final RegistroService service;

    public RegistroControl(RegistroService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RegistroResponseDTO>>> listar() {
        List<RegistroResponseDTO> lista = service.listar();
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Lista de registros obtenida", lista)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RegistroResponseDTO>> guardar(@RequestBody RegistroRequestDTO dto) {
        RegistroResponseDTO registro = service.guardar(dto);
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.CREATED.value(), "Registro creado exitosamente", registro),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RegistroResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(registro -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Registro encontrado", registro)
                ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Registro no encontrado", null)
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Registro eliminado", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Registro no encontrado", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RegistroResponseDTO>> actualizar(@PathVariable Long id,
                                                                       @RequestBody RegistroRequestDTO dto) {
        return service.actualizar(id, dto)
                .map(actualizado -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Registro actualizado", actualizado)
                ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Registro no encontrado", null)
                ));
    }
}
