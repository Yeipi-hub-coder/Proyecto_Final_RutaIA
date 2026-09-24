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
    public void eliminar(Integer id) {
        Estudiante estudiante = buscarOFallar(id);
        estudianteRepository.delete(estudiante);
    }

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
        return DtoToResponse(estudiante);
    }

    @Override
    public EstudianteResponseDTO obtenerPorId(Integer id) {
        return DtoToResponse(buscarOFallar(id));
    }

    @Override
    public List<EstudianteResponseDTO> listar() {
        return estudianteRepository.findAll().stream()
                .map(this::DtoToResponse)
                .toList();
    }

    @Override
    public EstudianteResponseDTO actualizar(Integer id, EstudianteRequestDTO dto){
        Estudiante estudiante = buscarOFallar(id);
        if(dto.getNombre().equals("string") || dto.getNombre().isBlank()){
            throw new IllegalArgumentException("No Puede dejar los valores default");
        } else {
            estudiante.setNombre(dto.getNombre());
        }
        estudiante.setCorreo(dto.getCorreo());
        estudiante.setNivelExperiencia(dto.getNivelExperiencia());
        if (dto.getAreaInteres().equals("string") || dto.getAreaInteres().isBlank()){
            throw new IllegalArgumentException("No Puede dejar los valores default");
        } {
            estudiante.setAreaInteres(dto.getAreaInteres());
        }
        return DtoToResponse(estudianteRepository.save(estudiante));
    }

    @Override
    public Integer obtenerIdPorCorreo(String correo) {
        return estudianteRepository.findByCorreo(correo)
                .map(Estudiante::getId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    private Estudiante buscarOFallar(Integer id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un estudiante con id " + id));
    }

    private EstudianteResponseDTO DtoToResponse(Estudiante e) {
        return EstudianteResponseDTO.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .correo(e.getCorreo())
                .nivelExperiencia(e.getNivelExperiencia())
                .areaInteres(e.getAreaInteres())
                .build();
    }
}
