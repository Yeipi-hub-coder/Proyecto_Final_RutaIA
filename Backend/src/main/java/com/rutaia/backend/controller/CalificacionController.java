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
            summary = "Calificar una recomendación",
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
}
