package com.parcialtres.demo.controller;

import com.parcialtres.demo.dto.CrearCarritoRequest;
import com.parcialtres.demo.entity.CarritoDeCompras;
import com.parcialtres.demo.service.CarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    /**
     * Crear un carrito de compras (Endpoint protegido y transaccional)
     * Requiere autenticación JWT
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> crearCarrito(
            @Valid @RequestBody CrearCarritoRequest request,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            CarritoDeCompras carrito = carritoService.crearCarrito(email, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(carrito);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Listar productos de un carrito específico
     * Valida que el carrito pertenezca al usuario autenticado
     */
    @GetMapping("/{carritoId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> listarCarrito(
            @PathVariable Long carritoId,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            CarritoDeCompras carrito = carritoService.listarCarrito(email, carritoId);
            return ResponseEntity.ok(carrito);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Listar todos los carritos del usuario autenticado
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CarritoDeCompras>> listarMisCarritos(Authentication authentication) {
        String email = authentication.getName();
        List<CarritoDeCompras> carritos = carritoService.listarCarritosUsuario(email);
        return ResponseEntity.ok(carritos);
    }

    // DTO para respuestas de error
    record ErrorResponse(String mensaje) {}
}
