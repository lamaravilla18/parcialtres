package com.parcialtres.demo.controller;

import com.parcialtres.demo.entity.Producto;
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
    public ResponseEntity<List<Producto>> listarProductosConStockMenor(
            @RequestParam(name = "cantidad") Integer cantidad) {
        List<Producto> productos = productoService.listarProductosConStockMenorA(cantidad);
        return ResponseEntity.ok(productos);
    }

    /**
     * Endpoint público para listar todos los productos
     * @return Lista de todos los productos
     */
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }
}
