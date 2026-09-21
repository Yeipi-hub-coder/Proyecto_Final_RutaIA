package com.rutaia.backend.controller;

import com.rutaia.backend.dto.Response.EstadisticasResponseDTO;
import com.rutaia.backend.service.EstadisticasService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
@Tag(name = "Estadisticas")
public class EstadisticasController {

    private final EstadisticasService estadisticasService;

    // RF18
    @GetMapping
    public ResponseEntity<EstadisticasResponseDTO> obtener() {
        return ResponseEntity.ok(estadisticasService.obtener());
    }
}
