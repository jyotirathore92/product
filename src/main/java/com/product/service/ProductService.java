package com.product.service;

import com.product.entity.Product;
import com.product.model.ProductCurrencyResponse;

import java.util.List;

public interface ProductService {
    List<ProductCurrencyResponse> getProducts();
    Product addProduct(Product product);
    Product getProduct(Long id);
}
