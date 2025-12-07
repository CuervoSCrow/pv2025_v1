package com.puntoventa.pv2025_v1.repository;

import com.puntoventa.pv2025_v1.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository
        extends JpaRepository<Producto, Long> {
}
