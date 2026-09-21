package com.rutaia.backend.service.impl;

import com.rutaia.backend.dto.Response.ConsultaResponseDTO;
import com.rutaia.backend.dto.Request.EstudianteRequestDTO;
import com.rutaia.backend.dto.Response.EstudianteResponseDTO;
import com.rutaia.backend.model.Consulta;
import com.rutaia.backend.model.Estudiante;
import com.rutaia.backend.exception.BusinessException;
import com.rutaia.backend.exception.ResourceNotFoundException;
import com.rutaia.backend.repository.ConsultaRepository;
import com.rutaia.backend.repository.EstudianteRepository;
import com.rutaia.backend.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final ConsultaRepository consultaRepository;

    @Override
    public EstudianteResponseDTO registrar(EstudianteRequestDTO dto) {
        // Regla de negocio: un correo solo puede pertenecer a un estudiante (RN01)
        if (estudianteRepository.existsByCorreo(dto.getCorreo())) {
            throw new BusinessException("Ya existe un estudiante registrado con ese correo");
        }

        Estudiante estudiante = Estudiante.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .nivelExperiencia(dto.getNivelExperiencia())
                .areaInteres(dto.getAreaInteres())
                .build();

        estudiante = estudianteRepository.save(estudiante);
        return toResponse(estudiante);
    }

    @Override
    public EstudianteResponseDTO obtenerPorId(Integer id) {
        return toResponse(buscarOFallar(id));
    }

    @Override
    public List<EstudianteResponseDTO> listar() {
        return estudianteRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<ConsultaResponseDTO> historial(Integer id) {
        buscarOFallar(id); // valida que el estudiante exista (RF02)
        List<Consulta> consultas = consultaRepository.findByEstudianteIdOrderByFechaDesc(id);
        return consultas.stream()
                .map(c -> ConsultaResponseDTO.builder()
                        .id(c.getId())
                        .estudianteId(c.getEstudiante().getId())
                        .pregunta(c.getPregunta())
                        .fecha(c.getFecha())
                        .estado(c.getEstado())
                        .build())
                .toList();
    }

    private Estudiante buscarOFallar(Integer id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un estudiante con id " + id));
    }

    private EstudianteResponseDTO toResponse(Estudiante e) {
        return EstudianteResponseDTO.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .correo(e.getCorreo())
                .nivelExperiencia(e.getNivelExperiencia())
                .areaInteres(e.getAreaInteres())
                .build();
    }
}
