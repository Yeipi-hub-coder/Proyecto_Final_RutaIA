package com.rutaia.backend.model;

import com.rutaia.backend.model.enums.EstadoFinal;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recomendacion")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Recomendacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;


    @Column(name = "respuesta_generada", nullable = false)
    private String respuestaGenerada;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_final", nullable = false)
    private EstadoFinal estadoFinal;

    @OneToMany(mappedBy = "recomendacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Fuente> fuentes = new ArrayList<>();
}
