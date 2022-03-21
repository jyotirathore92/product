package com.product.utils;

import com.product.entity.Product;
import com.product.model.ForeignRateResponse;
import com.product.model.ProductCurrencyResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class TestDataGen {

    public static List<Product> getListOfProduct() {
        Product productOne = new Product();
        productOne.setId(1L);
        productOne.setName("Apple");
        productOne.setDescription("iPhone 12 Pro");
        productOne.setPrice(new BigDecimal("1100"));

        Product productTwo = new Product();
        productTwo.setId(2L);
        productTwo.setName("Samsung");
        productTwo.setDescription("Galaxy S20");
        productTwo.setPrice(new BigDecimal("1100"));

        List<Product> productList = new ArrayList<>();
        productList.add(productOne);
        productList.add(productTwo);

        return productList;
    }

    public static ForeignRateResponse getRate() {
        ForeignRateResponse response = new ForeignRateResponse();
        response.setSuccess(true);
        response.setDate(new Date());
        response.setTimestamp(1519296206L);
        response.setBase("EUR");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("AUD", new BigDecimal("1.566015"));
        rates.put("USD", new BigDecimal("1.23396"));
        response.setRates(rates);

        return response;
    }

    public static ForeignRateResponse getFailureForForeignRate() {
        ForeignRateResponse response = new ForeignRateResponse();
        response.setSuccess(false);
        return response;
    }

    public static List<ProductCurrencyResponse> getResponse() {
//        ForeignRateResponse response = new ForeignRateResponse();
//        response.setSuccess(true);
//        response.setDate(new Date());
//        response.setTimestamp(1519296206L);
//        response.setBase("EUR");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("AUD", new BigDecimal("1.566015"));
        rates.put("USD", new BigDecimal("1.23396"));
       // response.setRates(rates);

        ProductCurrencyResponse productOne = new ProductCurrencyResponse();
        productOne.setId(1L);
        productOne.setName("Apple");
        productOne.setDescription("iPhone 12 Pro");
        productOne.setPrice(rates);

        ProductCurrencyResponse productTwo = new ProductCurrencyResponse();
        productTwo.setId(2L);
        productTwo.setName("Samsung");
        productTwo.setDescription("Galaxy S20");
        productTwo.setPrice(rates);

        List<ProductCurrencyResponse> productList = new ArrayList<>();
        productList.add(productOne);
        productList.add(productTwo);

        return productList;
    }
}
