package com.duoc.API_ChileHub.controllers;

import com.duoc.API_ChileHub.Assemblers.ProductModelAssembler;
import com.duoc.API_ChileHub.model.Product;
import com.duoc.API_ChileHub.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Operaciones para la clase 'Product'")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    ProductModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Devuelve todos los productos registrados")
    public Object getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return assembler.toCollectionModel(products);
    }

    @PostMapping
    @Operation(summary = "Añadir Producto", description = "Registra un producto a la base de datos. Requiere: nombre, precio, descripcion y imagen")
    public EntityModel<Product> addProduct(@RequestBody Product product) {
        Product nuevo = productService.addProduct(product);
        if (nuevo != null) {
            return assembler.toModel(nuevo);
        }else {
            return null;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Producto", description = "Busca un producto con la id ingresada")
    public EntityModel<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return assembler.toModel(product);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar Producto", description = "Borra el usuario con la ID ingresada")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Producto", description = "Actualiza un producto. Requiere: nombre, precio, descripcion y imagen")
    public EntityModel<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        Product nuevoProduct = productService.updateProduct(id,  product);
        if(nuevoProduct != null) {
            return  assembler.toModel(nuevoProduct);
        } else {
            return null;
        }
    }
}
