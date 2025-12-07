package com.puntoventa.pv2025_v1.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.puntoventa.pv2025_v1.model.enums.EstadoProducto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@JsonPropertyOrder({"id", "nombre", "descripcion", "estadoProducto",
        "categoriaId", "unidadId", "costoActual", "precioVenta",
        "stockMinimo", "stockMaximo", "codigoBarras", "createAt"})
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private EstadoProducto estadoProducto;
    private Long categoriaId;
    private Long unidadId;
    private BigDecimal costoActual;
    private BigDecimal precioVenta;
    private Integer stockMinimo;
    private Integer stockMaximo;
    private String codigoBarras;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaActualizacion;

}
