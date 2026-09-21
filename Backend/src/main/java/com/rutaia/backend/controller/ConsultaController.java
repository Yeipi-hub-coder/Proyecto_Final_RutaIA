package com.rutaia.backend.controller;

import com.rutaia.backend.dto.Request.ConsultaRequestDTO;
import com.rutaia.backend.dto.Response.ConsultaResponseDTO;
import com.rutaia.backend.service.ConsultaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
@Tag(name = "Consultas y recomendaciones")
public class ConsultaController {

    private final ConsultaService consultaService;

    // RF06: unico punto de entrada del frontend para pedir una recomendacion.
    // El frontend NUNCA llama directamente a n8n, Qdrant u OpenRouter (RF06).
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> consultar(@Valid @RequestBody ConsultaRequestDTO dto) {
        return ResponseEntity.ok(consultaService.procesarConsulta(dto));
    }

    // RF15 / RF16 - ver el detalle completo de una consulta ya procesada
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(consultaService.obtenerPorId(id));
    }
}
