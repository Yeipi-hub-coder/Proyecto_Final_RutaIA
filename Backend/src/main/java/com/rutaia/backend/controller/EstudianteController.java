package com.rutaia.backend.controller;

import com.rutaia.backend.dto.Request.EstudianteRequestDTO;
import com.rutaia.backend.dto.Response.ConsultaResponseDTO;
import com.rutaia.backend.dto.Response.EstudianteResponseDTO;
import com.rutaia.backend.service.EstudianteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Estudiantes", description = "Registro y consulta de estudiantes, y su historial de consultas")
public class EstudianteController {

    private final EstudianteService estudianteService;

    @Operation(
            summary = "Registrar un estudiante",
            description = "Crea un nuevo estudiante. El correo debe ser único (RN01) y el nivel de " +
                    "experiencia debe ser PRINCIPIANTE, INTERMEDIO o AVANZADO. (RF01)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Estudiante creado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos (campos vacíos, correo mal formado, etc.)"),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe un estudiante con ese correo")
    })
    @PostMapping
    public ResponseEntity<EstudianteResponseDTO> registrar(@Valid @RequestBody EstudianteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.registrar(dto));
    }

    @Operation(
            summary = "Eliminar un estudiante",
            description = "Elimina un estudiante por su id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "403",
                    description = "El estudiante no se pudo eliminar"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Estudiante eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Estudiante no encontrado"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        estudianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Actualizar datos de estudiante",
            description = "Actualiza los datos de un estudiante que ya estaba registrado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estudiante actualizado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos (campos vacíos, correo mal formado, etc.)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un estudiante con ese id"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe otro estudiante con ese correo"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody EstudianteRequestDTO dto){
        return ResponseEntity.ok(estudianteService.actualizar(id, dto));
    }

    @Operation(
            summary = "Listar estudiantes",
            description = "Devuelve todos los estudiantes registrados en la plataforma."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado de estudiantes"
    )
    @GetMapping
    public ResponseEntity<List<EstudianteResponseDTO>> listar() {
        return ResponseEntity.ok(estudianteService.listar());
    }

    @Operation(
            summary = "Obtener ID de estudiante",
            description = "Obtiene el ID de un estudiante utilizando su correo electrónico."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "ID del estudiante obtenido correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un estudiante con el correo proporcionado"
            )
    })
    @GetMapping("/id")
    public ResponseEntity<Integer> obtenerIdPorCorreo(
            @RequestParam String correo
    ) {
        Integer estudianteId = estudianteService.obtenerIdPorCorreo(correo);
        return ResponseEntity.ok(estudianteId);
    }

    @Operation(
            summary = "Consultar un estudiante por id",
            description = "Devuelve los datos de un estudiante específico. (RF02)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estudiante encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un estudiante con ese id"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(estudianteService.obtenerPorId(id));
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
        return ResponseEntity.ok(estudianteService.historial(id));
    }
}
