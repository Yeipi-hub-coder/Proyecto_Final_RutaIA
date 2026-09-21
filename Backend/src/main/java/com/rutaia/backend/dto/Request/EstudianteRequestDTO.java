package com.rutaia.backend.dto.Request;

import com.rutaia.backend.model.enums.NivelExperiencia;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EstudianteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato valido")
    @Size(max = 100)
    private String correo;

    @NotNull(message = "El nivel de experiencia es obligatorio")
    private NivelExperiencia nivelExperiencia;

    @NotBlank(message = "El area de interes es obligatoria")
    @Size(max = 100)
    private String areaInteres;
}
