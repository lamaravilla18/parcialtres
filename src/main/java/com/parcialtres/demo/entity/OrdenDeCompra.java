package com.parcialtres.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordenes_compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenDeCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_orden", nullable = false, updatable = false)
    private LocalDateTime fechaOrden;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado; // PENDIENTE, PROCESADA, ENVIADA, ENTREGADA, CANCELADA

    @Column(name = "direccion_envio", length = 200)
    private String direccionEnvio;

    @Column(name = "metodo_pago", length = 50)
    private String metodoPago;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOrden> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        fechaOrden = LocalDateTime.now();
        if (estado == null) {
            estado = "PENDIENTE";
        }
    }

    /**
     * Calcula el total de la orden sumando todos los items
     */
    public void calcularTotal() {
        this.total = items.stream()
                .map(ItemOrden::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
