package com.parcialtres.demo.service;

import com.parcialtres.demo.entity.Producto;
import com.parcialtres.demo.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    /**
     * Lista todos los productos con stock menor a la cantidad especificada
     * @param cantidad Cantidad máxima de stock (no incluye el valor exacto)
     * @return Lista de productos con stock menor a la cantidad
     */
    public List<Producto> listarProductosConStockMenorA(Integer cantidad) {
        return productoRepository.findByStockLessThan(cantidad);
    }

    /**
     * Lista todos los productos
     * @return Lista de todos los productos
     */
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }
}
