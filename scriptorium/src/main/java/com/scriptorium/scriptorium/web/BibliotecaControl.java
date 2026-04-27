package com.scriptorium.scriptorium.web;

import java.util.Map;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.scriptorium.scriptorium.Service.BibliotecarioService;
import com.scriptorium.scriptorium.dto.BibliotecarioRequestDTO;
import com.scriptorium.scriptorium.dto.BibliotecarioResponseDTO;
import com.scriptorium.scriptorium.utils.ApiResponse;

@RestController
@RequestMapping("/bibliotecarios")
public class BibliotecaControl {

    private final BibliotecarioService service;

    public BibliotecaControl(BibliotecarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BibliotecarioResponseDTO>>> listarBibliotecarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BibliotecarioResponseDTO> lista = service.listar(pageable);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Lista de bibliotecarios obtenida", lista));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BibliotecarioResponseDTO>> guardar(@RequestBody BibliotecarioRequestDTO dto) {
        BibliotecarioResponseDTO creado = service.guardar(dto);
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.CREATED.value(), "Bibliotecario creado exitosamente", creado),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BibliotecarioResponseDTO>> obtenerPorId(@PathVariable Long id) {
        Optional<BibliotecarioResponseDTO> opt = service.obtenerPorId(id);
        return opt.map(bib -> ResponseEntity.ok(
                new ApiResponse<>(200, "Bibliotecario encontrado", bib)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Bibliotecario no encontrado", null)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Bibliotecario eliminado", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Bibliotecario no encontrado", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BibliotecarioResponseDTO>> actualizar(@PathVariable Long id,
            @RequestBody BibliotecarioRequestDTO dto) {
                System.out.println("Contraseña recibida: " + dto.getContraseña());
        return service.actualizar(id, dto)
                .map(actualizado -> ResponseEntity.ok(
                        new ApiResponse<>(200, "Bibliotecario actualizado", actualizado)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(404, "Bibliotecario no encontrado", null)));
    }

    @GetMapping("/verificar-nombre")
    public ResponseEntity<ApiResponse<Map<String, String>>> verificarNombre(@RequestParam String nombre) {
        boolean existe = service.verificarNombre(nombre);
        if (existe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiResponse<>(409, "El nombre de usuario ya está en uso",
                            Map.of("mensaje", "El nombre de usuario ya está en uso")));
        } else {
            return ResponseEntity.ok(
                    new ApiResponse<>(200, "El nombre de usuario está disponible",
                            Map.of("mensaje", "El nombre de usuario está disponible")));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String usuario = credenciales.get("usuario");
        String contraseña = credenciales.get("contraseña");
        if (contraseña == null) {
            contraseña = credenciales.get("contrasena");
        }

        if (usuario == null || contraseña == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario y contraseña son requeridos");
        }

        return service.login(usuario, contraseña)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas"));
    }

}
