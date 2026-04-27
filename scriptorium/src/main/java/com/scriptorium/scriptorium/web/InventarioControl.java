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

import com.scriptorium.scriptorium.Service.InventarioService;
import com.scriptorium.scriptorium.dto.InventarioRequestDTO;
import com.scriptorium.scriptorium.dto.InventarioResponseDTO;
import com.scriptorium.scriptorium.utils.ApiResponse;

@RestController
@RequestMapping("/inventario")
public class InventarioControl {

    private final InventarioService service;

    public InventarioControl(InventarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventarioResponseDTO>>> listar() {
        List<InventarioResponseDTO> lista = service.listar();
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Lista de inventarios obtenida", lista)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InventarioResponseDTO>> guardar(@RequestBody InventarioRequestDTO dto) {
        InventarioResponseDTO inventario = service.guardar(dto);
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.CREATED.value(), "Inventario creado exitosamente", inventario),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InventarioResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(inventario -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Inventario encontrado", inventario)
                ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Inventario no encontrado", null)
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Inventario eliminado", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Inventario no encontrado", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InventarioResponseDTO>> actualizar(@PathVariable Long id,
                                                                         @RequestBody InventarioRequestDTO dto) {
        return service.actualizar(id, dto)
                .map(actualizado -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Inventario actualizado", actualizado)
                ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Inventario no encontrado", null)
                ));
    }
}
