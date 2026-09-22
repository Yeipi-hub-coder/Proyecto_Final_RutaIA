package com.rutaia.backend.controller;

import com.rutaia.backend.dto.Request.CursoRequestDTO;
import com.rutaia.backend.dto.Response.CursoResponseDTO;
import com.rutaia.backend.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@Tag(name = "Cursos", description = "Gestión administrativa de cursos y catálogo público Las operaciones de administración no requieren autenticación, se prueban directamente aquí en Swagger.")
public class CursoController {

    private final CursoService cursoService;

    @Operation(
            summary = "Registrar un curso (admin)",
            description = "Crea un curso nuevo con estado ACTIVO. El nombre y la descripción son " +
                    "obligatorios y la duración debe ser mayor que cero. (RF03 / RN03 / RN04)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Curso creado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
             )
    })
    @PostMapping
    public ResponseEntity<CursoResponseDTO> registrar(@Valid @RequestBody CursoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.registrar(dto));
    }

    @Operation(
            summary = "Consultar un curso por id (admin)",
            description = "Devuelve un curso sin importar su estado (ACTIVO o INACTIVO). (RF03)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Curso encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un curso con ese id"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(cursoService.obtenerPorId(id));
    }

    @Operation(
            summary = "Actualizar un curso (admin)",
            description = "Reemplaza nombre, descripción, categoría, nivel y duración de un curso existente. (RF03)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Curso actualizado"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un curso con ese id"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody CursoRequestDTO dto) {
        return ResponseEntity.ok(cursoService.actualizar(id, dto));
    }

    @Operation(
            summary = "Desactivar un curso (admin)",
            description = "Marca el curso como INACTIVO en lugar de borrarlo físicamente. Un curso inactivo deja de aparecer en el catálogo y ya no puede recomendarse."    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Curso desactivado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un curso con ese id"
            )
    })
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Integer id) {
        cursoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Ver catálogo de cursos activos",
            description = "Endpoint público consumido por el frontend. Solo devuelve cursos con " +
                    "estado ACTIVO y permite filtrar opcionalmente por categoría y/o nivel " +
                    "(BASICO, INTERMEDIO, AVANZADO). (RF04)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Catálogo de cursos activos"
    )
    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> catalogo(
            @Parameter(description = "Filtra por categoría exacta (ej: 'Desarrollo web')", example = "Desarrollo web")
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String nivel) {
        return ResponseEntity.ok(cursoService.catalogo(categoria, nivel));
    }
}
