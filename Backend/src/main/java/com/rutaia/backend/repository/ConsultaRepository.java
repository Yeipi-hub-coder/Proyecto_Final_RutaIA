package com.rutaia.backend.repository;

import com.rutaia.backend.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByEstudianteIdOrderByFechaAsc(Integer estudianteId);
    long countByEstado(com.rutaia.backend.model.enums.EstadoConsulta estado);
}
