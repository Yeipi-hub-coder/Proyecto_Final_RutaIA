package com.rutaia.backend.dto.n8n;

import lombok.*;

import java.math.BigDecimal;

// Una fuente tal como la devuelve el flujo de n8n
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class N8nFuenteDTO {
    private Integer cursoId;
    private BigDecimal similitud;
}
