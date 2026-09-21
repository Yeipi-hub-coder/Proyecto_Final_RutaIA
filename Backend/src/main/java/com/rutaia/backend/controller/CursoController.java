package com.rutaia.backend.controller;

import com.rutaia.backend.dto.Request.CursoRequestDTO;
import com.rutaia.backend.dto.Response.CursoResponseDTO;
import com.rutaia.backend.service.CursoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@Tag(name = "Cursos")
public class CursoController {

    private final CursoService cursoService;

    // RF03 - operacion administrativa (probar via Swagger/Postman)
    @PostMapping
    public ResponseEntity<CursoResponseDTO> registrar(@Valid @RequestBody CursoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.registrar(dto));
    }

    // RF03
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(cursoService.obtenerPorId(id));
    }

    // RF03
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> actualizar(@PathVariable Integer id,
                                                         @Valid @RequestBody CursoRequestDTO dto) {
        return ResponseEntity.ok(cursoService.actualizar(id, dto));
    }

    // RF03 - desactivar (no se elimina fisicamente el curso)
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Integer id) {
        cursoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    // RF04 - catalogo publico, solo cursos activos, con filtros opcionales
    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> catalogo(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String nivel) {
        return ResponseEntity.ok(cursoService.catalogo(categoria, nivel));
    }
}
