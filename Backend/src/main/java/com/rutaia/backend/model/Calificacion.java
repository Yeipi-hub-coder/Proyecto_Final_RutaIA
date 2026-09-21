package com.rutaia.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "calificacion")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recomendacion_id", nullable = false, unique = true)
    private Recomendacion recomendacion;

    @Column(nullable = false)
    private Integer puntuacion;

    @Column(length = 500)
    private String comentario;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();
}
