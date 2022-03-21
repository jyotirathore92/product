package com.product.controller;

import com.product.exception.CustomEntityNotFoundException;
import com.product.exception.CustomInternalServerError;
import com.product.model.ProductCurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.product.entity.Product;
import com.product.service.ProductServiceImpl;

import java.util.List;

@RestController
public class ProductController {
	
	@Autowired
	private ProductServiceImpl productService;

	@GetMapping("/products")
    public ResponseEntity<List<ProductCurrencyResponse>> getProducts() {
		List<ProductCurrencyResponse> product;
        try {
        	product = productService.getProducts();
        } catch (CustomInternalServerError e) {
            throw new CustomInternalServerError(e.getMessage());
        }
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product;
        try {
            product = productService.getProduct(id);
        } catch (CustomEntityNotFoundException e) {
            throw new CustomEntityNotFoundException(e.getMessage());
        }

        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            product = productService.addProduct(product);
        } catch (Exception e) {
            throw new CustomInternalServerError("Something went wrong while adding new product. Check payload.");
        }

        return ResponseEntity.ok().body(product);
    }

}
