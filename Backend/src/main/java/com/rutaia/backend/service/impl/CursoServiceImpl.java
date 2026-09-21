package com.rutaia.backend.service.impl;

import com.rutaia.backend.dto.Request.CursoRequestDTO;
import com.rutaia.backend.dto.Response.CursoResponseDTO;
import com.rutaia.backend.model.Curso;
import com.rutaia.backend.model.enums.EstadoCurso;
import com.rutaia.backend.model.enums.NivelCurso;
import com.rutaia.backend.exception.ResourceNotFoundException;
import com.rutaia.backend.repository.CursoRepository;
import com.rutaia.backend.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    @Override
    public CursoResponseDTO registrar(CursoRequestDTO dto) {
        Curso curso = Curso.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .categoria(dto.getCategoria())
                .nivel(dto.getNivel())
                .duracion(dto.getDuracion())
                .estado(EstadoCurso.ACTIVO)
                .build();
        return toResponse(cursoRepository.save(curso));
    }

    @Override
    public CursoResponseDTO obtenerPorId(Integer id) {
        return toResponse(buscarOFallar(id));
    }

    @Override
    public CursoResponseDTO actualizar(Integer id, CursoRequestDTO dto) {
        Curso curso = buscarOFallar(id);
        curso.setNombre(dto.getNombre());
        curso.setDescripcion(dto.getDescripcion());
        curso.setCategoria(dto.getCategoria());
        curso.setNivel(dto.getNivel());
        curso.setDuracion(dto.getDuracion());
        return toResponse(cursoRepository.save(curso));
    }

    @Override
    public void desactivar(Integer id) {
        Curso curso = buscarOFallar(id);
        curso.setEstado(EstadoCurso.INACTIVO);
        cursoRepository.save(curso);
        // Nota: si implementas sincronizacion con Qdrant, aqui deberias
        // disparar la eliminacion/actualizacion del vector correspondiente.
    }

    @Override
    public List<CursoResponseDTO> catalogo(String categoria, String nivel) {
        List<Curso> cursos;
        boolean tieneCategoria = StringUtils.hasText(categoria);
        boolean tieneNivel = StringUtils.hasText(nivel);

        if (tieneCategoria && tieneNivel) {
            cursos = cursoRepository.findByEstadoAndCategoriaIgnoreCaseAndNivel(
                    EstadoCurso.ACTIVO, categoria, NivelCurso.valueOf(nivel.toUpperCase()));
        } else if (tieneCategoria) {
            cursos = cursoRepository.findByEstadoAndCategoriaIgnoreCase(EstadoCurso.ACTIVO, categoria);
        } else if (tieneNivel) {
            cursos = cursoRepository.findByEstadoAndNivel(EstadoCurso.ACTIVO, NivelCurso.valueOf(nivel.toUpperCase()));
        } else {
            cursos = cursoRepository.findByEstado(EstadoCurso.ACTIVO);
        }

        return cursos.stream().map(this::toResponse).toList();
    }

    private Curso buscarOFallar(Integer id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un curso con id " + id));
    }

    private CursoResponseDTO toResponse(Curso c) {
        return CursoResponseDTO.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .descripcion(c.getDescripcion())
                .categoria(c.getCategoria())
                .nivel(c.getNivel())
                .duracion(c.getDuracion())
                .estado(c.getEstado())
                .build();
    }
}
