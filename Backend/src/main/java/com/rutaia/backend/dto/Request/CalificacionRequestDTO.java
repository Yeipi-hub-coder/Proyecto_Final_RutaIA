package com.rutaia.backend.dto.Request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CalificacionRequestDTO {

    @NotNull(message = "El identificador de la recomendacion es obligatorio")
    private Integer recomendacionId;

    @NotNull(message = "La puntuacion es obligatoria")
    @Min(value = 1, message = "La puntuacion minima es 1")
    @Max(value = 5, message = "La puntuacion maxima es 5")
    private Integer puntuacion;

    @Size(max = 500)
    private String comentario;
}
