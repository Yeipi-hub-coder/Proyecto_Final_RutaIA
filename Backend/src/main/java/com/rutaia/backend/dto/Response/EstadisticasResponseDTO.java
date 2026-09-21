package com.rutaia.backend.dto.Response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EstadisticasResponseDTO {
    private long totalConsultas;
    private long consultasRespondidas;
    private long consultasSinResultados;
    private Double promedioCalificaciones;
    private String cursoMasRecomendado;
}
