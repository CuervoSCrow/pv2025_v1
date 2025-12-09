package com.puntoventa.pv2025_v1.controller;

import com.puntoventa.pv2025_v1.model.dto.ProductoRequest;
import com.puntoventa.pv2025_v1.model.dto.ProductoResponse;
import com.puntoventa.pv2025_v1.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/productos")
@RequiredArgsConstructor
@Tag(name = "Gestion de Productos",
     description = "API para la gestion de productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("✅ MarcaController funcionando - "+
                LocalDateTime.now());
    }
//============================= Crear un Producto ====================================
    @PostMapping()
    @Operation(summary = "Crear un producto")
    public ResponseEntity<?> crearProducto(
            @Valid @RequestBody
            ProductoRequest request){
        try{
            System.out.println("🎯 POST /api/productos llamado");
            System.out.println("📦 Nombre: " + request.getNombre());

            ProductoResponse producto = productoService.crearProducto(request);
            return new ResponseEntity<>(producto, HttpStatus.CREATED);
        }catch(Exception e){
            System.out.println("❌ Error creando producto: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }
//====================================== Buscar =============================================

//    ================================= Mostrar Todos los Productos =========================
    @GetMapping
    @Operation(summary = "Muestra todos los productos")
    public ResponseEntity<List<ProductoResponse>> mostrarTodosLosProductos(){
        try {
            List<ProductoResponse> productos = productoService.obtenerTodosLosProductos();
            return ResponseEntity.ok(productos);
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

//    ============================ Obtener productos por nombre ===============
    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos por nombre")
    public ResponseEntity<List<ProductoResponse>> buscarProductosPorNombre(
            @RequestParam("nombre") String nombre){
        try{
            List<ProductoResponse> productos =
                    productoService.buscarProductosPorNombre(nombre);
            return ResponseEntity.ok(productos);
        }catch(Exception e){
            System.out.println("❌ Error buscando productos por nombre: "
                    + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

//    ============================ Obtener productos por categoria ID ==================
    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Buscar productos por categoria ID")
    public ResponseEntity<List<ProductoResponse>> buscarProductosPorCategoriaId(
            @PathVariable("categoriaId") Long categoriaId){
        try{
            List<ProductoResponse> productos =
                    productoService.buscarProductosPorCategoriaId(categoriaId);
            return ResponseEntity.ok(productos);
        }catch(Exception e){
            System.out.println("❌ Error buscando productos por categoria ID: "
                    + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

//    ============================== Obtener productos por estado ====================
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar productos por estado")
    public ResponseEntity<List<ProductoResponse>> buscarProductosPorEstado(
            @PathVariable("estado") String estado){
        try{
            List<ProductoResponse> productos =
                    productoService.buscarProductosPorEstado(estado);
            return ResponseEntity.ok(productos);
        }catch(Exception e){
            System.out.println("❌ Error buscando productos por estado: "
                    + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
//    =========================== Obtener producto por codigo de barras ==================
    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Obtener producto por codigo")
    public ResponseEntity<ProductoResponse> obtenerProductoPorCodigo(
            @PathVariable("codigo") String codigo){
        try{
            ProductoResponse producto =
                    productoService.obtenerProductoPorCodigoBarras(codigo);
            return ResponseEntity.ok(producto);
        }catch(Exception e){
            System.out.println("❌ Error obteniendo producto por codigo: "
                    + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

//    =========================== Obtenerr por Codigo de Barras Parcial ==================
    @GetMapping("/buscar/codigoParcial")
    @Operation(summary = "Buscar productos por codigo de barras parcial")
    public ResponseEntity<List<ProductoResponse>> buscarProductosPorCodigoParcial(
            @RequestParam("codigoParcial") String codigoParcial){
        try{
            List<ProductoResponse> productos =
                    productoService.buscarProductosPorCodigoParcial(codigoParcial);
            return ResponseEntity.ok(productos);
        }catch(Exception e){
            System.out.println("❌ Error buscando productos por codigo parcial: "
                    + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

//    ============================ Obtener producto por ID =====================
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<ProductoResponse> obtenerProductoPorId(
            @PathVariable("id") Long id){
        try{
            ProductoResponse producto =
                    productoService.obtenerProductoPorId(id);
            return ResponseEntity.ok(producto);
        }catch(Exception e){
            System.out.println("❌ Error obteniendo producto por ID: "
                    + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

//    ============================= Obtener productos por precio de venta ==================
    @GetMapping("/buscar/precio")
    @Operation(summary = "Buscar productos por precio de venta")
    public ResponseEntity<List<ProductoResponse>> buscarProductosPorPrecioVenta(
            @RequestParam("precioVenta") Double precioVenta){
        try{
            List<ProductoResponse> productos =
                    productoService.buscarProductosPorPrecioVenta(precioVenta);
            return ResponseEntity.ok(productos);
        }catch(Exception e){
            System.out.println("❌ Error buscando productos por precio de venta: "
                    + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
//========================================= Eliminacion ==================================

//    =========================== ELiminar Producto (Cambiar estado a INACTIVO) ====================
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto (cambiar estado a INACTIVO)")
    public ResponseEntity<String> eliminarProducto(
            @PathVariable("id") Long id){
        try{
            productoService.eliminarProducto(id);
            return ResponseEntity.ok("✅ Producto marcado como INACTIVO - ID: " + id);
        }catch(Exception e){
            System.out.println("❌ Error eliminando producto: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

//    &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& ACTUALIZACION &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

//    ============================ Actualizar Producto ============================
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto")
    public ResponseEntity<?> actualizarProducto(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductoRequest request) {
        try {
            System.out.println("🎯 PUT /api/productos/" + id + " llamado");
            System.out.println("📦 Nombre: " + request.getNombre());
            ProductoResponse producto =
                    productoService.actualizarProducto(id, request);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            System.out.println("❌ Error actualizando producto: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

//    ============================== Cambiar estado de un producto ==========================
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de un producto")
    public ResponseEntity<?> cambiarEstadoProducto(
            @PathVariable("id") Long id,
            @RequestParam("estado") String estado) {
        try {
            System.out.println("🎯 PATCH /api/productos/" + id + "/estado llamado");
            System.out.println("📦 Nuevo estado: " + estado);
            ProductoResponse producto =
                    productoService.cambiarEstadoProducto(id, estado);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            System.out.println("❌ Error cambiando estado de producto: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }



}
