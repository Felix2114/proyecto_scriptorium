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

import com.scriptorium.scriptorium.Service.UsuarioService;
import com.scriptorium.scriptorium.dto.UsuarioRequestDTO;
import com.scriptorium.scriptorium.dto.UsuarioResponseDTO;
import com.scriptorium.scriptorium.utils.ApiResponse;

@RestController
@RequestMapping("/Usuarios")
public class UsuarioControl {

        private final UsuarioService service;

        public UsuarioControl(UsuarioService service) {
                this.service = service;
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<UsuarioResponseDTO>>> listar() {
                List<UsuarioResponseDTO> lista = service.listar();
                return ResponseEntity.ok(
                                new ApiResponse<>(200, "Lista de usuarios obtenida", lista));
        }

        @PostMapping
        public ResponseEntity<ApiResponse<UsuarioResponseDTO>> guardar(@RequestBody UsuarioRequestDTO dto) {
                UsuarioResponseDTO usuario = service.guardar(dto);
                return new ResponseEntity<>(
                                new ApiResponse<>(HttpStatus.CREATED.value(), "Usuario creado exitosamente", usuario),
                                HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<UsuarioResponseDTO>> obtenerPorId(@PathVariable Long id) {
                return service.obtenerPorId(id)
                                .map(usuario -> ResponseEntity.ok(
                                                new ApiResponse<>(200, "Usuario encontrado", usuario)))
                                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                                new ApiResponse<>(404, "Usuario no encontrado", null)));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
                if (service.eliminar(id)) {
                        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario eliminado", null));
                } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(new ApiResponse<>(404, "Usuario no encontrado", null));
                }
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<UsuarioResponseDTO>> actualizar(@PathVariable Long id,
                        @RequestBody UsuarioRequestDTO dto) {
                return service.actualizar(id, dto)
                                .map(actualizado -> ResponseEntity.ok(
                                                new ApiResponse<>(200, "Usuario actualizado", actualizado)))
                                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                                new ApiResponse<>(404, "Usuario no encontrado", null)));
        }

        @GetMapping("/buscar-usuarios")
        public ResponseEntity<ApiResponse<List<UsuarioResponseDTO>>> buscarUsuarios(@RequestParam String palabra) {
                List<UsuarioResponseDTO> resultados = service.buscarUsuarios(palabra);

                if (resultados.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                        new ApiResponse<>(404, "No se encontraron usuarios", List.of()));
                } else {
                        return ResponseEntity.ok(
                                        new ApiResponse<>(200, "Usuarios encontrados", resultados));
                }
        }

}
