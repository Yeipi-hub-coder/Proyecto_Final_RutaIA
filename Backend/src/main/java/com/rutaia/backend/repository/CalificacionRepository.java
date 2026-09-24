package com.rutaia.backend.repository;

import com.rutaia.backend.model.Calificacion;
import com.rutaia.backend.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {
    boolean existsByRecomendacionId(Integer recomendacionId);

    @org.springframework.data.jpa.repository.Query("select avg(c.puntuacion) from Calificacion c")
    Double promedioPuntuacion();
}
