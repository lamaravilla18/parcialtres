package com.parcialtres.demo.service;

import com.parcialtres.demo.dto.CrearCarritoRequest;
import com.parcialtres.demo.dto.ItemCarritoRequest;
import com.parcialtres.demo.entity.*;
import com.parcialtres.demo.repository.CarritoDeComprasRepository;
import com.parcialtres.demo.repository.ProductoRepository;
import com.parcialtres.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoDeComprasRepository carritoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Crea un carrito de compras de forma transaccional
     * - Descontando el stock de los productos
     * - Validando disponibilidad
     */
    @Transactional
    public CarritoDeCompras crearCarrito(String email, CrearCarritoRequest request) {
        // Buscar el usuario autenticado
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear el carrito
        CarritoDeCompras carrito = new CarritoDeCompras();
        carrito.setUsuario(usuario);
        carrito.setEstado("ACTIVO");
        carrito.setTotal(BigDecimal.ZERO);

        // Procesar cada item del carrito
        for (ItemCarritoRequest itemRequest : request.getItems()) {
            // Buscar el producto
            Producto producto = productoRepository.findById(itemRequest.getProductoId())
                    .orElseThrow(() -> new RuntimeException(
                            "Producto no encontrado: " + itemRequest.getProductoId()));

            // Validar que haya stock suficiente
            if (producto.getStock() < itemRequest.getCantidad()) {
                throw new RuntimeException(
                        "Stock insuficiente para el producto: " + producto.getNombre() +
                        ". Stock disponible: " + producto.getStock() +
                        ", solicitado: " + itemRequest.getCantidad());
            }

            // Descontar el stock (TRANSACCIONAL)
            producto.setStock(producto.getStock() - itemRequest.getCantidad());
            productoRepository.save(producto);

            // Crear el item del carrito
            ItemCarrito item = new ItemCarrito();
            item.setProducto(producto);
            item.setCantidad(itemRequest.getCantidad());
            item.setPrecioUnitario(producto.getPrecio());
            // Calcular el subtotal inmediatamente antes de agregar al carrito
            item.calcularYEstablecerSubtotal();

            // Agregar el item al carrito
            carrito.agregarItem(item);
        }

        // Guardar el carrito con todos sus items
        return carritoRepository.save(carrito);
    }

    /**
     * Lista los productos de un carrito específico
     * Valida que el carrito pertenezca al usuario autenticado
     */
    public CarritoDeCompras listarCarrito(String email, Long carritoId) {
        // Buscar el usuario autenticado
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar el carrito y validar que pertenezca al usuario
        CarritoDeCompras carrito = carritoRepository.findByIdAndUsuarioId(carritoId, usuario.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Carrito no encontrado o no pertenece al usuario"));

        return carrito;
    }

    /**
     * Lista todos los carritos de un usuario
     */
    public java.util.List<CarritoDeCompras> listarCarritosUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return carritoRepository.findByUsuarioId(usuario.getId());
    }
}
