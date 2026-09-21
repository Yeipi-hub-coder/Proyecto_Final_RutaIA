package com.rutaia.backend.dto.Response;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CalificacionResponseDTO {
    private Integer id;
    private Integer recomendacionId;
    private Integer puntuacion;
    private String comentario;
    private LocalDateTime fecha;
}
