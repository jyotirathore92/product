package com.product.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductCurrencyResponse {

    private Long id;
    private String name;
    private String description;
    private Map<String, BigDecimal> price;
}
