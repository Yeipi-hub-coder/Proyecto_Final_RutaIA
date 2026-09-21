package com.rutaia.backend.dto.Request;

import com.rutaia.backend.model.enums.NivelCurso;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CursoRequestDTO {

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "La descripcion del curso es obligatoria")
    @Size(max = 500)
    private String descripcion;

    @NotBlank(message = "La categoria es obligatoria")
    @Size(max = 100)
    private String categoria;

    @NotNull(message = "El nivel es obligatorio")
    private NivelCurso nivel;

    @NotNull(message = "La duracion es obligatoria")
    @Positive(message = "La duracion debe ser mayor que cero")
    private Integer duracion;
}
