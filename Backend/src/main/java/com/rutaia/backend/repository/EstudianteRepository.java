package com.rutaia.backend.repository;

import com.rutaia.backend.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    boolean existsByCorreo(String correo);
    Optional<Estudiante> findByCorreo(String correo);
}
