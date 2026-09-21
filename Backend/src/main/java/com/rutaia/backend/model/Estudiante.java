package com.rutaia.backend.model;

import com.rutaia.backend.model.enums.NivelExperiencia;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estudiante")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_experiencia", nullable = false)
    private NivelExperiencia nivelExperiencia;

    @Column(name = "area_interes", nullable = false, length = 100)
    private String areaInteres;
}
