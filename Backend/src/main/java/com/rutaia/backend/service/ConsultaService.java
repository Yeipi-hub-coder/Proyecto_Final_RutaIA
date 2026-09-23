package com.rutaia.backend.service;

import com.rutaia.backend.dto.Request.ConsultaRequestDTO;
import com.rutaia.backend.dto.Response.ConsultaResponseDTO;

import java.util.List;

public interface ConsultaService {
    ConsultaResponseDTO procesarConsulta(ConsultaRequestDTO dto);
    ConsultaResponseDTO obtenerPorId(Integer id);
    List<ConsultaResponseDTO> listar();
}
