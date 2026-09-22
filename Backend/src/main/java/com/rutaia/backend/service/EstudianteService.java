package com.rutaia.backend.service;

import com.rutaia.backend.dto.Response.ConsultaResponseDTO;
import com.rutaia.backend.dto.Request.EstudianteRequestDTO;
import com.rutaia.backend.dto.Response.EstudianteResponseDTO;

import java.util.List;

public interface EstudianteService {
    EstudianteResponseDTO registrar(EstudianteRequestDTO dto);
    EstudianteResponseDTO obtenerPorId(Integer id);
    List<EstudianteResponseDTO> listar();
    List<ConsultaResponseDTO> historial(Integer id);
    EstudianteResponseDTO actualizar(Integer id, EstudianteRequestDTO dto);
}
