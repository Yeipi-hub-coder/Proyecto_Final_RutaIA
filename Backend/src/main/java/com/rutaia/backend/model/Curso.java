package com.rutaia.backend.model;

import com.rutaia.backend.model.enums.EstadoCurso;
import com.rutaia.backend.model.enums.NivelCurso;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "curso")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false, length = 100)
    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelCurso nivel;

    @Column(nullable = false)
    private Integer duracion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoCurso estado = EstadoCurso.ACTIVO;
}
