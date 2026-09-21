package com.rutaia.backend.service.impl;

import com.rutaia.backend.dto.Response.EstadisticasResponseDTO;
import com.rutaia.backend.model.enums.EstadoConsulta;
import com.rutaia.backend.repository.CalificacionRepository;
import com.rutaia.backend.repository.ConsultaRepository;
import com.rutaia.backend.repository.RecomendacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadisticasServiceImpl implements com.rutaia.backend.service.EstadisticasService {

    private final ConsultaRepository consultaRepository;
    private final CalificacionRepository calificacionRepository;
    private final RecomendacionRepository recomendacionRepository;

    @Override
    public EstadisticasResponseDTO obtener() {
        long total = consultaRepository.count();
        long respondidas = consultaRepository.countByEstado(EstadoConsulta.RESPONDIDA);
        long sinResultados = consultaRepository.countByEstado(EstadoConsulta.SIN_RESULTADOS);
        Double promedio = calificacionRepository.promedioPuntuacion();

        List<Object[]> ranking = recomendacionRepository.cursoMasRecomendado();
        String cursoTop = ranking.isEmpty() ? null : (String) ranking.get(0)[1];

        return EstadisticasResponseDTO.builder()
                .totalConsultas(total)
                .consultasRespondidas(respondidas)
                .consultasSinResultados(sinResultados)
                .promedioCalificaciones(promedio)
                .cursoMasRecomendado(cursoTop)
                .build();
    }
}
