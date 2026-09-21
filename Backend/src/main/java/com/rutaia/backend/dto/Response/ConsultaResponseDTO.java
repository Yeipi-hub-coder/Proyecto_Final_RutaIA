package com.rutaia.backend.dto.Response;

import com.rutaia.backend.model.enums.EstadoConsulta;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConsultaResponseDTO {
    private Integer id;
    private Integer estudianteId;
    private String pregunta;
    private LocalDateTime fecha;
    private EstadoConsulta estado;
    // Se llena solo cuando ya existe una recomendacion asociada
    private RecomendacionResponseDTO recomendacion;
}
