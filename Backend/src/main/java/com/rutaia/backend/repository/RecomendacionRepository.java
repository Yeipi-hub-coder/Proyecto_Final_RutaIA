package com.rutaia.backend.repository;

import com.rutaia.backend.model.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecomendacionRepository extends JpaRepository<Recomendacion, Integer> {
    Optional<Recomendacion> findByConsultaId(Integer consultaId);

    @org.springframework.data.jpa.repository.Query("""
        select f.curso.id, f.curso.nombre, count(f)
        from Fuente f
        group by f.curso.id, f.curso.nombre
        order by count(f) desc
        """)
    java.util.List<Object[]> cursoMasRecomendado();
}
