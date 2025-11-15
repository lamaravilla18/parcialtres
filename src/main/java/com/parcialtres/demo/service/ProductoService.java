package com.parcialtres.demo.service;

import com.parcialtres.demo.dto.ProductoResponse;
import com.parcialtres.demo.entity.Producto;
import com.parcialtres.demo.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    private ProductoResponse convertirADto(Producto producto) {
        ProductoResponse dto = new ProductoResponse();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        return dto;
    }

    /**
     * Lista todos los productos con stock menor a la cantidad especificada
     * 
     * @param cantidad Cantidad máxima de stock (no incluye el valor exacto)
     * @return Lista de productos con stock menor a la cantidad
     */
    public List<ProductoResponse> listarProductosConStockMenorA(Integer cantidad) {
        return productoRepository.findByStockLessThan(cantidad).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos los productos
     * 
     * @return Lista de todos los productos
     */
    public List<ProductoResponse> listarTodos() {
        return productoRepository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }
}
