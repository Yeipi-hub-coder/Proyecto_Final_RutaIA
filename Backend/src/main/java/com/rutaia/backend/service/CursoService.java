package com.rutaia.backend.service;

import com.rutaia.backend.dto.Request.CursoRequestDTO;
import com.rutaia.backend.dto.Response.CursoResponseDTO;

import java.util.List;

public interface CursoService {
    CursoResponseDTO registrar(CursoRequestDTO dto);
    CursoResponseDTO obtenerPorId(Integer id);
    CursoResponseDTO actualizar(Integer id, CursoRequestDTO dto);
    void desactivar(Integer id);
    List<CursoResponseDTO> catalogo(String categoria, String nivel);
}
