package com.rutaia.backend.controller;

import com.rutaia.backend.dto.Response.ConsultaResponseDTO;
import com.rutaia.backend.dto.Request.EstudianteRequestDTO;
import com.rutaia.backend.dto.Response.EstudianteResponseDTO;
import com.rutaia.backend.service.EstudianteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
@Tag(name = "Estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    // RF01
    @PostMapping
    public ResponseEntity<EstudianteResponseDTO> registrar(@Valid @RequestBody EstudianteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.registrar(dto));
    }

    // RF02
    @GetMapping
    public ResponseEntity<List<EstudianteResponseDTO>> listar() {
        return ResponseEntity.ok(estudianteService.listar());
    }

    // RF02
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(estudianteService.obtenerPorId(id));
    }

    // RF02 / RF16
    @GetMapping("/{id}/historial")
    public ResponseEntity<List<ConsultaResponseDTO>> historial(@PathVariable Integer id) {
        return ResponseEntity.ok(estudianteService.historial(id));
    }
}
