package com.rutaia.backend.controller;

import com.rutaia.backend.dto.Response.EstadisticasResponseDTO;
import com.rutaia.backend.service.EstadisticasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
@Tag(name = "Estadisticas", description = "Métricas generales de uso de la plataforma")
public class EstadisticasController {

    private final EstadisticasService estadisticasService;

    @Operation(
            summary = "Ver estadísticas generales",
            description = "Devuelve el total de consultas, cuántas fueron respondidas, cuántas quedaron sin resultados, el promedio de calificaciones y el curso más recomendado hasta el momento"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Estadísticas calculadas correctamente"
    )
    @GetMapping
    public ResponseEntity<EstadisticasResponseDTO> obtener() {
        return ResponseEntity.ok(estadisticasService.obtener());
    }
}
