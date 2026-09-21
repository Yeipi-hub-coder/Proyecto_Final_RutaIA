package com.rutaia.backend.dto.Response;

import com.rutaia.backend.model.enums.EstadoCurso;
import com.rutaia.backend.model.enums.NivelCurso;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CursoResponseDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private NivelCurso nivel;
    private Integer duracion;
    private EstadoCurso estado;
}
