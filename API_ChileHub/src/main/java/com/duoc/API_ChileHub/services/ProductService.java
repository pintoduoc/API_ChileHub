package com.duoc.API_ChileHub.services;

import com.duoc.API_ChileHub.model.Product;
import com.duoc.API_ChileHub.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    //Listar todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //Buscar producto por ID
    public Product getProductById(int id) {
        if(productRepository.existsById(id)){
            return productRepository.findById(id).get();
        }else{
            return null;
        }
    }

    //Agregar nuevo producto
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    //Eliminar producto por ID
    public boolean deleteProduct(int id) {
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    //Actualizar producto por ID
    public Product updateProduct(int id, Product product) {
        if (productRepository.existsById(id)) {
            Product buscado = productRepository.findById(id).get();
            buscado.setName(product.getName());
            buscado.setPrice(product.getPrice());
            buscado.setDescription(product.getDescription());
            buscado.setImage(product.getImage());
            productRepository.save(buscado);
            return product;
        }else {
            return null;
        }
    }
}
