package com.rutaia.backend.service;

import com.rutaia.backend.dto.Request.ConsultaRequestDTO;
import com.rutaia.backend.dto.Response.ConsultaResponseDTO;

public interface ConsultaService {
    // Orquesta todo el flujo: registra la consulta, llama a n8n, guarda la recomendacion (RF06-RF14)
    ConsultaResponseDTO procesarConsulta(ConsultaRequestDTO dto);
    ConsultaResponseDTO obtenerPorId(Integer id);
}
