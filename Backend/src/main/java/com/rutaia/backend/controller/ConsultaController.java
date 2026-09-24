package com.rutaia.backend.controller;

import com.rutaia.backend.dto.Request.ConsultaRequestDTO;
import com.rutaia.backend.dto.Response.ConsultaResponseDTO;
import com.rutaia.backend.service.ConsultaService;
import com.rutaia.backend.service.EstudianteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
@Tag(name = "Consultas y recomendaciones", description = "Punto de entrada único para pedir una recomendación en lenguaje natural. El frontend solo habla con estos endpoints: nunca llama directamente a n8n, Qdrant u OpenRouter")
public class ConsultaController {

    private final ConsultaService consultaService;

    @Operation(
            summary = "Hacer una consulta en lenguaje natural",
            description = """
                    Registra la consulta del estudiante y orquesta todo el flujo RAG:
                    1) guarda la consulta con estado PENDIENTE
                    2) envía la pregunta a n8n (RF08), que genera el embedding , busca en 
                    Qdrant aplicando el umbral de relevancia y genera la respuesta con el 
                    modelo
                    3) guarda la recomendación y sus fuentes, y actualiza el estado final de la 
                    consulta a RESPONDIDA, SIN_RESULTADOS o ERROR (RF13 / RF14).

                    Si la pregunta no tiene relación con el catálogo (ej: "quiero aprender cocina 
                    italiana"), el estado final será SIN_RESULTADOS."""
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta procesada (revisa el campo 'estado' de la respuesta: puede ser RESPONDIDA, SIN_RESULTADOS o ERROR)"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "La pregunta está vacía o el estudianteId falta"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un estudiante con ese id"
            )
    })
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> consultar(@Valid @RequestBody ConsultaRequestDTO dto) {
        return ResponseEntity.ok(consultaService.procesarConsulta(dto));
    }

    @Operation(
            summary = "Listar consultas",
            description = "Devuelve todos las consultas registradas en la plataforma."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado de consultas"
    )
    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listar(){
        return ResponseEntity.ok(consultaService.listar());
    }

    @Operation(
            summary = "Ver el detalle de una consulta",
            description = "Devuelve la pregunta, su estado y, si ya fue procesada, la recomendación generada junto con las fuentes (cursos) y su similitud."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe una consulta con ese id"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(consultaService.obtenerPorId(id));
    }

    @Operation(
            summary = "Historial de consultas de un estudiante",
            description = "Devuelve todas las consultas que ha realizado el estudiante, de la más reciente a la más antigua, junto con su estado y recomendación si ya fue procesada."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Historial del estudiante"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un estudiante con ese id"
            )
    })
    @GetMapping("/{id}/historial")
    public ResponseEntity<List<ConsultaResponseDTO>> historial(@PathVariable Integer id) {
        return ResponseEntity.ok(consultaService.historial(id));
    }
}
