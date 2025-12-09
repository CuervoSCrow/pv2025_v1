package com.puntoventa.pv2025_v1.repository;

import com.puntoventa.pv2025_v1.model.entity.Producto;
import com.puntoventa.pv2025_v1.model.enums.EstadoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository
        extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByPrecioVenta(Double precioVenta);

    List<Producto> findByCategoriaId(Long categoriaId);

    List<Producto> findByEstado(EstadoProducto estado);
}
