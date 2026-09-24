package com.rutaia.backend.controller;

import com.rutaia.backend.dto.Request.CalificacionRequestDTO;
import com.rutaia.backend.dto.Response.CalificacionResponseDTO;
import com.rutaia.backend.service.CalificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Calificar una recomendacion",
            description = "Registra una puntuación de 1 a 5 y un comentario opcional para una recomendación puntual. Solo se permite una calificación por recomendación"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Calificación registrada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Puntuación fuera de rango (debe ser entre 1 y 5)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe una recomendación con ese id"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Esa recomendación ya fue calificada"
            )
    })
    @PostMapping
    public ResponseEntity<CalificacionResponseDTO> calificar(@Valid @RequestBody CalificacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calificacionService.calificar(dto));
    }

    @Operation(
            summary = "Obtener el id de recomendación a partir del id de consulta",
            description = "El frontend normalmente solo tiene a mano el consultaId. Este endpoint " +
                    "devuelve el recomendacionId asociado a esa consulta, necesario para poder " +
                    "calificarla con POST /api/calificaciones."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Id de la recomendación encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "La consulta no existe o aún no tiene una recomendación asociada"
            )
    })
    @GetMapping("/recomendacion-por-consulta/{consultaId}")
    public ResponseEntity<Integer> obtenerRecomendacionIdPorConsulta(
            @PathVariable Integer consultaId) {
        return ResponseEntity.ok(calificacionService.obtenerRecomendacionIdPorConsulta(consultaId));
    }
}
