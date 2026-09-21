package com.rutaia.backend.model;

import com.rutaia.backend.model.enums.EstadoConsulta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consulta")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @Column(nullable = false, length = 500)
    private String pregunta;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoConsulta estado = EstadoConsulta.PENDIENTE;
}
