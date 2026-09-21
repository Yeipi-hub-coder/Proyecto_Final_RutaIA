package com.rutaia.backend.service;

import com.rutaia.backend.dto.Request.CalificacionRequestDTO;
import com.rutaia.backend.dto.Response.CalificacionResponseDTO;

public interface CalificacionService {
    CalificacionResponseDTO calificar(CalificacionRequestDTO dto);
}
