package com.parcialtres.demo.service;

import com.parcialtres.demo.dto.CarritoResponse;
import com.parcialtres.demo.dto.CrearCarritoRequest;
import com.parcialtres.demo.dto.ItemCarritoRequest;
import com.parcialtres.demo.dto.ItemCarritoResponse;
import com.parcialtres.demo.entity.*;
import com.parcialtres.demo.repository.CarritoDeComprasRepository;
import com.parcialtres.demo.repository.ProductoRepository;
import com.parcialtres.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoDeComprasRepository carritoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    // Método auxiliar para convertir ItemCarrito a DTO
    private ItemCarritoResponse convertirItemADto(ItemCarrito item) {
        ItemCarritoResponse dto = new ItemCarritoResponse();
        dto.setId(item.getId());
        dto.setProductoId(item.getProducto().getId());
        dto.setProductoNombre(item.getProducto().getNombre());
        dto.setCantidad(item.getCantidad());
        dto.setPrecioUnitario(item.getPrecioUnitario());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }

    // Método auxiliar para convertir CarritoDeCompras a DTO
    private CarritoResponse convertirCarritoADto(CarritoDeCompras carrito) {
        CarritoResponse dto = new CarritoResponse();
        dto.setId(carrito.getId());
        dto.setUsuarioId(carrito.getUsuario().getId());
        dto.setUsuarioNombre(carrito.getUsuario().getEmail());
        dto.setFechaCreacion(carrito.getFechaCreacion());
        dto.setTotal(carrito.getTotal());
        dto.setEstado(carrito.getEstado());
        dto.setCantidadItems(carrito.getItems() != null ? carrito.getItems().size() : 0);

        if (carrito.getItems() != null) {
            List<ItemCarritoResponse> itemsDto = carrito.getItems().stream()
                    .map(this::convertirItemADto)
                    .collect(Collectors.toList());
            dto.setItems(itemsDto);
        }

        return dto;
    }

    /**
     * Crea un carrito de compras de forma transaccional
     * - Descontando el stock de los productos
     * - Validando disponibilidad
     */
    @Transactional
    public CarritoResponse crearCarrito(String email, CrearCarritoRequest request) {
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
        CarritoDeCompras carritoGuardado = carritoRepository.save(carrito);

        // Convertir a DTO antes de retornar
        return convertirCarritoADto(carritoGuardado);
    }

    /**
     * Lista los productos de un carrito específico
     * Valida que el carrito pertenezca al usuario autenticado
     */
    public CarritoResponse listarCarrito(String email, Long carritoId) {
        // Buscar el usuario autenticado
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar el carrito y validar que pertenezca al usuario
        CarritoDeCompras carrito = carritoRepository.findByIdAndUsuarioId(carritoId, usuario.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Carrito no encontrado o no pertenece al usuario"));

        return convertirCarritoADto(carrito);
    }

    /**
     * Lista todos los carritos de un usuario
     */
    public List<CarritoResponse> listarCarritosUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<CarritoDeCompras> carritos = carritoRepository.findByUsuarioId(usuario.getId());

        return carritos.stream()
                .map(this::convertirCarritoADto)
                .collect(Collectors.toList());
    }
}
