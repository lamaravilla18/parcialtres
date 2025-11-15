package com.parcialtres.demo.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ComentarioResponse {
    private Long id;
    private String contenido;
    private Integer calificacion;
    private LocalDateTime fechaCreacion;
    private Long usuarioId;
    private String usuarioEmail;
    private Long productoId;
    private String productoNombre;
}
