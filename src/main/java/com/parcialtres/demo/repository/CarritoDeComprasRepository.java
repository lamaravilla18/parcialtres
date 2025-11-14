package com.parcialtres.demo.repository;

import com.parcialtres.demo.entity.CarritoDeCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoDeComprasRepository extends JpaRepository<CarritoDeCompras, Long> {
    
    List<CarritoDeCompras> findByUsuarioId(Long usuarioId);
    
    Optional<CarritoDeCompras> findByIdAndUsuarioId(Long id, Long usuarioId);
    
    List<CarritoDeCompras> findByEstado(String estado);
    
    List<CarritoDeCompras> findByUsuarioIdAndEstado(Long usuarioId, String estado);
}
