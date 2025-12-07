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
}
