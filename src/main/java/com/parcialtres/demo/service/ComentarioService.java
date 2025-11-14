package com.parcialtres.demo.service;

import com.parcialtres.demo.entity.Comentario;
import com.parcialtres.demo.repository.ComentarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    /**
     * Lista comentarios creados a partir de una fecha específica (inclusive)
     * @param fecha Fecha mínima de creación (se convierte a inicio del día)
     * @return Lista de comentarios creados desde la fecha indicada
     */
    public List<Comentario> listarComentariosDesde(LocalDate fecha) {
        LocalDateTime fechaHora = fecha.atStartOfDay();
        return comentarioRepository.findByFechaCreacionGreaterThanEqual(fechaHora);
    }

    /**
     * Lista todos los comentarios
     * @return Lista de todos los comentarios
     */
    public List<Comentario> listarTodos() {
        return comentarioRepository.findAll();
    }
}
