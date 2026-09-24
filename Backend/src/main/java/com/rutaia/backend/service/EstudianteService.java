package com.rutaia.backend.service;

import com.rutaia.backend.dto.Response.ConsultaResponseDTO;
import com.rutaia.backend.dto.Request.EstudianteRequestDTO;
import com.rutaia.backend.dto.Response.EstudianteResponseDTO;

import java.util.List;

public interface EstudianteService {
    EstudianteResponseDTO registrar(EstudianteRequestDTO dto);
    EstudianteResponseDTO obtenerPorId(Integer id);
    List<EstudianteResponseDTO> listar();
    EstudianteResponseDTO actualizar(Integer id, EstudianteRequestDTO dto);
    Integer obtenerIdPorCorreo(String correo);
    void eliminar(Integer id);
}
