package com.rutaia.backend.service;

import com.rutaia.backend.dto.Request.ConsultaRequestDTO;
import com.rutaia.backend.dto.Response.ConsultaResponseDTO;

public interface ConsultaService {
    ConsultaResponseDTO procesarConsulta(ConsultaRequestDTO dto);
    ConsultaResponseDTO obtenerPorId(Integer id);
}
