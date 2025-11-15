package com.parcialtres.demo.service;

import com.parcialtres.demo.dto.ComentarioResponse;
import com.parcialtres.demo.entity.Comentario;
import com.parcialtres.demo.repository.ComentarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    private ComentarioResponse convertirADto(Comentario comentario) {
        ComentarioResponse dto = new ComentarioResponse();
        dto.setId(comentario.getId());
        dto.setContenido(comentario.getContenido());
        dto.setCalificacion(comentario.getCalificacion());
        dto.setFechaCreacion(comentario.getFechaCreacion());
        dto.setUsuarioId(comentario.getUsuario().getId());
        dto.setUsuarioEmail(comentario.getUsuario().getEmail());
        dto.setProductoId(comentario.getProducto().getId());
        dto.setProductoNombre(comentario.getProducto().getNombre());
        return dto;
    }

    /**
     * Lista comentarios creados a partir de una fecha específica (inclusive)
     * @param fecha Fecha mínima de creación (se convierte a inicio del día)
     * @return Lista de comentarios creados desde la fecha indicada
     */
    public List<ComentarioResponse> listarComentariosDesde(LocalDate fecha) {
        LocalDateTime fechaHora = fecha.atStartOfDay();
        return comentarioRepository.findByFechaCreacionGreaterThanEqual(fechaHora).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos los comentarios
     * @return Lista de todos los comentarios
     */
    public List<ComentarioResponse> listarTodos() {
        return comentarioRepository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
}
