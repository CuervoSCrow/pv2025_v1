package com.puntoventa.pv2025_v1.service;

import com.puntoventa.pv2025_v1.model.dto.ProductoRequest;
import com.puntoventa.pv2025_v1.model.dto.ProductoResponse;
import com.puntoventa.pv2025_v1.model.entity.Producto;
import com.puntoventa.pv2025_v1.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public ProductoResponse crearProducto(ProductoRequest productoRequest){
        try{
            System.out.println("🔧 [PRODUCTO SERVICE] Creando producto: "+ productoRequest.getNombre());

//            Crear entidad Producto
            Producto producto = new Producto();
            producto.setNombre(productoRequest.getNombre());
            producto.setDescripcion(productoRequest.getDescripcion());
            producto.setCategoriaId(productoRequest.getCategoriaId());
            producto.setUnidadId(productoRequest.getUnidadId());
            producto.setCostoActual(productoRequest.getCostoActual());
            producto.setPrecioVenta(productoRequest.getPrecioVenta());
            producto.setStockMinimo(productoRequest.getStockMinimo());
            producto.setStockMaximo(productoRequest.getStockMaximo());
            producto.setCodigo(productoRequest.getCodigoBarras());
            producto.setFechaCreacion(productoRequest.getFechaCreacion());
            producto.setFechaActualizacion(productoRequest.getFechaActualizacion());

            Producto productoGuardado = productoRepository.save(producto);
            System.out.println("✅ Producto guardado con ID: " + productoGuardado.getId());

//            Convertir a Response
            return convertirAResponse(productoGuardado);
        }catch(Exception e){
            System.out.println("❌ Error en ProductoService.crearProducto: " + e.getMessage());
            throw e;
        }
    }

//    ============  Obtener todos los Productos ======================
    public List<ProductoResponse> obtenerTodosLosProductos(){
        try{
            List<Producto> productos = productoRepository.findAll();
            return productos.stream()
                    .map(this::convertirAResponse)
                    .collect((Collectors.toList()));
        }catch(Exception e){
            System.out.println("❌ Error obteniendo productos: " + e.getMessage());
            throw e;
        }
    }

    //            Metodo para Convertir Entity a Response
    private ProductoResponse convertirAResponse(Producto producto){
        ProductoResponse response = new ProductoResponse();
        response.setId(producto.getId());
        response.setNombre(producto.getNombre());
        response.setDescripcion(producto.getDescripcion());
        response.setEstadoProducto(producto.getEstado());
        response.setCategoriaId(producto.getCategoriaId());
        response.setUnidadId(producto.getUnidadId());
        response.setCostoActual(producto.getCostoActual());
        response.setPrecioVenta(producto.getPrecioVenta());
        response.setStockMinimo(producto.getStockMinimo());
        response.setStockMaximo(producto.getStockMaximo());
        response.setCodigoBarras(producto.getCodigo());
        response.setFechaCreacion(producto.getFechaCreacion());
        response.setFechaActualizacion(producto.getFechaActualizacion());

        return response;
    }

}
