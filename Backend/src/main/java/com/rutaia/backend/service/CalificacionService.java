package com.rutaia.backend.service;

import com.rutaia.backend.dto.Request.CalificacionRequestDTO;
import com.rutaia.backend.dto.Response.CalificacionResponseDTO;

public interface CalificacionService {
    CalificacionResponseDTO calificar(CalificacionRequestDTO dto);

    // Le permite al frontend obtener el recomendacionId (necesario para calificar)
    // a partir del consultaId, que es lo unico que normalmente tiene a mano.
    Integer obtenerRecomendacionIdPorConsulta(Integer consultaId);

}
