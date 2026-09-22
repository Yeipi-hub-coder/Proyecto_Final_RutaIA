package com.rutaia.backend.service.impl;

import com.rutaia.backend.dto.Request.CalificacionRequestDTO;
import com.rutaia.backend.dto.Response.CalificacionResponseDTO;
import com.rutaia.backend.model.Calificacion;
import com.rutaia.backend.model.Recomendacion;
import com.rutaia.backend.exception.BusinessException;
import com.rutaia.backend.exception.ResourceNotFoundException;
import com.rutaia.backend.repository.CalificacionRepository;
import com.rutaia.backend.repository.RecomendacionRepository;
import com.rutaia.backend.service.CalificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final RecomendacionRepository recomendacionRepository;

    @Override
    public CalificacionResponseDTO calificar(CalificacionRequestDTO dto) {
        Recomendacion recomendacion = recomendacionRepository.findById(dto.getRecomendacionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe una recomendacion con id " + dto.getRecomendacionId()));

        // funcionalidad: una sola calificacion por recomendacion
        if (calificacionRepository.existsByRecomendacionId(dto.getRecomendacionId())) {
            throw new BusinessException("Esta recomendacion ya fue calificada");
        }

        Calificacion calificacion = Calificacion.builder()
                .recomendacion(recomendacion)
                .puntuacion(dto.getPuntuacion())
                .comentario(dto.getComentario())
                .build();
        calificacion = calificacionRepository.save(calificacion);

        return CalificacionResponseDTO.builder()
                .id(calificacion.getId())
                .recomendacionId(recomendacion.getId())
                .puntuacion(calificacion.getPuntuacion())
                .comentario(calificacion.getComentario())
                .fecha(calificacion.getFecha())
                .build();
    }
}
