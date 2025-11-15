package com.parcialtres.demo.controller;

import com.parcialtres.demo.dto.ProductoResponse;
import com.parcialtres.demo.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Endpoint público para listar productos con stock menor a una cantidad especificada
     * @param cantidad Cantidad máxima de stock (query param)
     * @return Lista de productos con stock menor a la cantidad
     */
    @GetMapping("/stock-menor")
    public ResponseEntity<List<ProductoResponse>> listarProductosConStockMenor(
            @RequestParam(name = "cantidad") Integer cantidad) {
        List<ProductoResponse> productos = productoService.listarProductosConStockMenorA(cantidad);
        return ResponseEntity.ok(productos);
    }

    /**
     * Endpoint público para listar todos los productos
     * @return Lista de todos los productos
     */
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listarTodos() {
        List<ProductoResponse> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }
}
