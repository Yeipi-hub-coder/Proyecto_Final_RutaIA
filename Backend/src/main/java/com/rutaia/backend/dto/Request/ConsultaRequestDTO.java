package com.rutaia.backend.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConsultaRequestDTO {

    @NotNull(message = "El identificador del estudiante es obligatorio")
    private Integer estudianteId;

    @NotBlank(message = "La pregunta no puede estar vacia")
    @jakarta.validation.constraints.Size(max = 500)
    private String pregunta;
}
