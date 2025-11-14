package com.parcialtres.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoResponse {
    
    private Long id;
    private Long usuarioId;
    private String usuarioNombre;
    private LocalDateTime fechaCreacion;
    private BigDecimal total;
    private String estado;
    private Integer cantidadItems;
    private List<ItemCarritoResponse> items;
}
