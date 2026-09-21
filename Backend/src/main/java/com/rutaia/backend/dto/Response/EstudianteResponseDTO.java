package com.rutaia.backend.dto.Response;

import com.rutaia.backend.model.enums.NivelExperiencia;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EstudianteResponseDTO {
    private Integer id;
    private String nombre;
    private String correo;
    private NivelExperiencia nivelExperiencia;
    private String areaInteres;
}
