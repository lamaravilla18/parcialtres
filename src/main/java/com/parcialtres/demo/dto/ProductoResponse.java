package com.parcialtres.demo.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private String categoria;
}
