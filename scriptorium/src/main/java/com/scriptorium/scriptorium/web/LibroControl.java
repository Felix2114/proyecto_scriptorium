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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scriptorium.scriptorium.Service.LibroService;
import com.scriptorium.scriptorium.dto.LibroRequestDTO;
import com.scriptorium.scriptorium.dto.LibroResponseDTO;
import com.scriptorium.scriptorium.utils.ApiResponse;

@RestController
@RequestMapping("/libro")
public class LibroControl {

        private final LibroService service;

        public LibroControl(LibroService service) {
                this.service = service;
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<LibroResponseDTO>>> listar() {
                List<LibroResponseDTO> lista = service.listar();
                return ResponseEntity.ok(
                                new ApiResponse<>(200, "Lista de libros obtenida", lista));
        }

        @GetMapping("/buscar-libros")
        public ResponseEntity<ApiResponse<List<LibroResponseDTO>>> buscarLibros(@RequestParam String palabra) {
                List<LibroResponseDTO> resultados = service.buscar(palabra);

                if (resultados.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                        new ApiResponse<>(404, "No se encontraron libros", List.of()));
                } else {
                        return ResponseEntity.ok(
                                        new ApiResponse<>(200, "Libros encontrados", resultados));
                }
        }

        @PostMapping
        public ResponseEntity<ApiResponse<LibroResponseDTO>> guardar(@RequestBody LibroRequestDTO dto) {
                LibroResponseDTO libro = service.guardar(dto);
                return new ResponseEntity<>(
                                new ApiResponse<>(HttpStatus.CREATED.value(), "Libro creado exitosamente", libro),
                                HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<LibroResponseDTO>> obtenerPorId(@PathVariable Long id) {
                return service.obtenerPorId(id)
                                .map(libro -> ResponseEntity.ok(
                                                new ApiResponse<>(200, "Libro encontrado", libro)))
                                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                                new ApiResponse<>(404, "Libro no encontrado", null)));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
                if (service.eliminar(id)) {
                        return ResponseEntity.ok(new ApiResponse<>(200, "Libro eliminado", null));
                } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(new ApiResponse<>(404, "Libro no encontrado", null));
                }
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<LibroResponseDTO>> actualizar(@PathVariable Long id,
                        @RequestBody LibroRequestDTO dto) {
                return service.actualizar(id, dto)
                                .map(actualizado -> ResponseEntity.ok(
                                                new ApiResponse<>(200, "Libro actualizado", actualizado)))
                                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                                new ApiResponse<>(404, "Libro no encontrado", null)));
        }
}
