package com.rutaia.backend.controller;

import com.rutaia.backend.dto.Request.CalificacionRequestDTO;
import com.rutaia.backend.dto.Response.CalificacionResponseDTO;
import com.rutaia.backend.service.CalificacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calificaciones")
@RequiredArgsConstructor
@Tag(name = "Calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    // RF17
    @PostMapping
    public ResponseEntity<CalificacionResponseDTO> calificar(@Valid @RequestBody CalificacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calificacionService.calificar(dto));
    }
}
