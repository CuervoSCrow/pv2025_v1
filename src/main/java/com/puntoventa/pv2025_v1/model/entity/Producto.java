package com.puntoventa.pv2025_v1.model.entity;

import com.puntoventa.pv2025_v1.model.enums.EstadoProducto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)// Audioria Automatica
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 250, nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoProducto estado = EstadoProducto.ACTIVO;

//    @Column(name = "proveedor")
//    private Proveedor   Proveedor;

//    @Column(name = "variante")
//    private Variante variante;

//    @Colum(name="marca")
//    private Marca marca;
    @CreatedDate
    @Column(name = "fecha_creacion",nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion",nullable = false)
    private LocalDateTime fechaActualizacion;

    @Column(name = "categoria_id")
    private Long categoriaId;

    @Column(name = "unidad_id")
    private Long unidadId;

    @Column(name = "costo_actual")
    private BigDecimal costoActual;

    @Column(name = "precio_venta")
    private BigDecimal precioVenta;

    @Column(name = "stock_minimo")
    private int stockMinimo;

    @Column(name = "stock_maximo")
    private int stockMaximo;

    @Column(name = "codigo_barras", length = 50)
    private String codigo;

    @CreatedBy
    @Column(name="create_by")
    private String createBy;

    @LastModifiedBy
    @Column(name = "update_by")
    private String updateBy;

    @PrePersist
    protected void onCreate(){
        if(fechaCreacion == null){
            fechaCreacion = LocalDateTime.now();
        }
        if(fechaActualizacion == null){
            fechaActualizacion = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate(){
        fechaActualizacion = LocalDateTime.now();
    }


}
