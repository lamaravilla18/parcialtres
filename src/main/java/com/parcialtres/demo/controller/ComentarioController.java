package com.parcialtres.demo.controller;

import com.parcialtres.demo.entity.Comentario;
import com.parcialtres.demo.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    /**
     * Endpoint público para listar comentarios desde una fecha específica
     * @param fecha Fecha mínima de creación (formato: yyyy-MM-dd)
     * @return Lista de comentarios creados desde la fecha indicada
     */
    @GetMapping("/desde-fecha")
    public ResponseEntity<List<Comentario>> listarComentariosDesde(
            @RequestParam(name = "fecha") 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Comentario> comentarios = comentarioService.listarComentariosDesde(fecha);
        return ResponseEntity.ok(comentarios);
    }

    /**
     * Endpoint público para listar todos los comentarios
     * @return Lista de todos los comentarios
     */
    @GetMapping
    public ResponseEntity<List<Comentario>> listarTodos() {
        List<Comentario> comentarios = comentarioService.listarTodos();
        return ResponseEntity.ok(comentarios);
    }
}
