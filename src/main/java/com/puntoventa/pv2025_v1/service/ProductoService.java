package com.puntoventa.pv2025_v1.service;

import com.puntoventa.pv2025_v1.model.dto.ProductoRequest;
import com.puntoventa.pv2025_v1.model.dto.ProductoResponse;
import com.puntoventa.pv2025_v1.model.entity.Producto;
import com.puntoventa.pv2025_v1.model.enums.EstadoProducto;
import com.puntoventa.pv2025_v1.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

//    ======================= Busquedas ==================================
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
//    ================== Obtener producto por ID ========================
    public ProductoResponse obtenerProductoPorId(Long id){
        try{
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
            return convertirAResponse(producto);
        }catch(Exception e){
            System.out.println("❌ Error obteniendo producto por ID: " + e.getMessage());
            throw e;
        }
    }

//    ============== Obtener productos por nombre =========================
    public List<ProductoResponse> buscarProductosPorNombre(String nombre){
        try{
            List<Producto> productos = productoRepository
                    .findByNombreContainingIgnoreCase(nombre);
            return productos.stream()
                    .map(this::convertirAResponse)
                    .collect(Collectors.toList());
        }catch(Exception e){
            System.out.println("❌ Error obteniendo productos por nombre: " + e.getMessage());
            throw e;
        }
    }
//    =================== Buscar por CategoriaId =========================
    public List<ProductoResponse> buscarProductosPorCategoriaId(Long categoriaId) {
        try {
            List<Producto> productos = productoRepository
                    .findByCategoriaId(categoriaId);
            return productos.stream()
                    .map(this::convertirAResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo productos por categoriaId: " + e.getMessage());
            throw e;
        }
    }
//    =================== Obtener Producto por Estado =========================
    public List<ProductoResponse> buscarProductosPorEstado(String estado){
        try{
//            Convertir String a EstadoProducto
            EstadoProducto estadoEnum;
            try{
                estadoEnum = EstadoProducto.valueOf(estado.toUpperCase());
            }catch(IllegalArgumentException e){
                System.out.println("❌ Estado no válido: " + estado);
                throw new RuntimeException("Estado no válido: " + estado +
                        ". Valores permitidos: " + Arrays.toString(EstadoProducto.values()));
            }
            List <Producto> productos = productoRepository
                    .findByEstado(estadoEnum);
            return productos.stream()
                    .map(this::convertirAResponse)
                    .collect(Collectors.toList());
        }catch(Exception e){
            System.out.println("❌ Error obteniendo productos por estado: " + e.getMessage());
            throw e;
        }
    }
//    =================== Obtener Producto por Codigo de Barras =========================
    public ProductoResponse obtenerProductoPorCodigoBarras(String codigoBarras){
        try{
            List<Producto> productos = productoRepository.findAll()
                    .stream()
                    .filter(p -> codigoBarras.equals(p.getCodigo()))
                    .collect(Collectors.toList());
            if(productos.isEmpty()){
                throw new RuntimeException("Producto no encontrado con codigo de barras: " + codigoBarras);
            }
            return convertirAResponse(productos.get(0));
        }catch(Exception e){
            System.out.println("❌ Error obteniendo producto por codigo de barras: " + e.getMessage());
            throw e;
        }
    }

//    ============================ Buscar Por Codigo Parcial =========================
    public List<ProductoResponse> buscarProductosPorCodigoParcial(String codigoParcial){
        try{
            List<Producto> productos = productoRepository.findAll()
                    .stream()
                    .filter(p -> p.getCodigo() != null &&
                            p.getCodigo().toLowerCase()
                                    .contains(codigoParcial.toLowerCase()))
                    .collect(Collectors.toList());
            return productos.stream()
                    .map(this::convertirAResponse)
                    .collect(Collectors.toList());
        }catch(Exception e){
            System.out.println("❌ Error obteniendo productos por codigo parcial: " + e.getMessage());
            throw e;
        }
    }
//
//    =================== Buscar Por PrecioVenta =========================
    public List<ProductoResponse> buscarProductosPorPrecioVenta(Double precioVenta){
        try{
            List<Producto> productos = productoRepository
                        .findByPrecioVenta(precioVenta);
            return productos.stream()
                    .map(this::convertirAResponse)
                    .collect(Collectors.toList());
        }catch(Exception e){
            System.out.println("❌ Error obteniendo productos por precio de venta: " + e.getMessage());
            throw e;
        }
    }

//    ======================================= Eliminacion ==================================
//    =========================== ELiminar Producto (Cambiar estado a INACTIVO) ====================
    public void eliminarProducto(Long id){
        try{
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Producto no encontrado con " +
                            "Id: "+id));
//            Cambiar estado a Inactivo en lugar de eliminar
            producto.setEstado(EstadoProducto.INACTIVO);
            productoRepository.save(producto);
            System.out.println("✅ Producto marcado como INACTIVO - ID: " + id);
        }catch(Exception e){
            System.out.println("❌ Error eliminando producto: " + e.getMessage());
            throw e;
        }
    }

//    &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Actualizar &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&6

//    ============================ Actualizar Producto =========================
    public ProductoResponse actualizarProducto(Long id, ProductoRequest request){
        try{
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Producto no encontrado " +
                            "con ID: "+id));
            producto.setNombre(request.getNombre());
            producto.setDescripcion(request.getDescripcion());
            producto.setCategoriaId(request.getCategoriaId());
            producto.setUnidadId(request.getUnidadId());
            producto.setCostoActual(request.getCostoActual());
            producto.setPrecioVenta(request.getPrecioVenta());
            producto.setStockMinimo(request.getStockMinimo());
            producto.setStockMaximo(request.getStockMaximo());
            producto.setCodigo(request.getCodigoBarras());
            producto.setFechaActualizacion(request.getFechaActualizacion());
            Producto productoActualizado = productoRepository.save(producto);
            System.out.println("✅ Producto actualizado con ID: " + productoActualizado.getId());
            return convertirAResponse(productoActualizado);
        }catch (Exception e){
            System.out.println("❌ Error actualizando producto: " + e.getMessage());
            throw e;
        }
    }
//    ================== Cambio de Estado del Producto =========================
    public ProductoResponse cambiarEstadoProducto(Long id, String nuevoEstado){
        try{
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Producto no encontrado " +
                            "con ID: "+id));
//            Convertir String a EstadoProducto
            EstadoProducto estadoEnum;
            try{
                estadoEnum = EstadoProducto.valueOf(nuevoEstado.toUpperCase());
            }catch(IllegalArgumentException e){
                System.out.println("❌ Estado no válido: " + nuevoEstado);
                throw new RuntimeException("Estado no válido: " + nuevoEstado +
                        ". Valores permitidos: " + Arrays.toString(EstadoProducto.values()));
            }
            producto.setEstado(estadoEnum);
            Producto productoActualizado = productoRepository.save(producto);
            System.out.println("✅ Estado del producto actualizado a " + nuevoEstado +
                    " para ID: " + productoActualizado.getId());
            return convertirAResponse(productoActualizado);
        }catch (Exception e){
            System.out.println("❌ Error cambiando estado del producto: " + e.getMessage());
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
