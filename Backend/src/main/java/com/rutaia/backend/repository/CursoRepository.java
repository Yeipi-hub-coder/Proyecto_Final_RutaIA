package com.rutaia.backend.repository;

import com.rutaia.backend.model.Curso;
import com.rutaia.backend.model.enums.EstadoCurso;
import com.rutaia.backend.model.enums.NivelCurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

    List<Curso> findByEstado(EstadoCurso estado);

    List<Curso> findByEstadoAndCategoriaIgnoreCaseAndNivel(
            EstadoCurso estado, String categoria, NivelCurso nivel);

    List<Curso> findByEstadoAndCategoriaIgnoreCase(EstadoCurso estado, String categoria);

    List<Curso> findByEstadoAndNivel(EstadoCurso estado, NivelCurso nivel);
}
