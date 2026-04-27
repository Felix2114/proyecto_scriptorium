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

import com.scriptorium.scriptorium.Service.GeneroService;
import com.scriptorium.scriptorium.dto.GeneroRequestDTO;
import com.scriptorium.scriptorium.dto.GeneroResponseDTO;
import com.scriptorium.scriptorium.utils.ApiResponse;

@RestController
@RequestMapping("/Genero")
public class GeneroControl {

    private final GeneroService service;

    public GeneroControl(GeneroService service) {
        this.service = service;
    }

     @GetMapping
    public ResponseEntity<ApiResponse<List<GeneroResponseDTO>>> listar() {
        List<GeneroResponseDTO> lista = service.listar();
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Lista de géneros obtenida", lista)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GeneroResponseDTO>> guardar(@RequestBody GeneroRequestDTO dto) {
        GeneroResponseDTO genero = service.guardar(dto);
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.CREATED.value(), "Género creado exitosamente", genero),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GeneroResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(genero -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Género encontrado", genero)
                ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Género no encontrado", null)
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Género eliminado", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Género no encontrado", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GeneroResponseDTO>> actualizar(@PathVariable Long id,
                                                                     @RequestBody GeneroRequestDTO dto) {
        return service.actualizar(id, dto)
                .map(actualizado -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Género actualizado", actualizado)
                ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Género no encontrado", null)
                ));
    }
}
