package com.puntoventa.pv2025_v1.model.dto;

import com.puntoventa.pv2025_v1.model.enums.EstadoProducto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductoRequest {

    private String nombre;

    private String  sku;

    private String descripcion;

    private EstadoProducto estadoProducto;

    private Long categoriaId;

    private Long unidadId;

    private BigDecimal costoActual;

    private BigDecimal precioVenta;

    private Integer stockMinimo;

    private Integer stockMaximo;

    private String codigoBarras;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;
}
