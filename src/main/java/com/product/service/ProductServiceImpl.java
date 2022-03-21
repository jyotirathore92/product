package com.product.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.product.exception.CustomInternalServerError;
import com.product.model.ProductCurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.product.entity.Product;
import com.product.exception.CustomEntityNotFoundException;
import com.product.model.ForeignRateResponse;
import com.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Value("${foreign.url}")
	private String url;
	
	@Value("${foreign.access-key}")
	private String accessKey;

	@Value("${foreign.symbols}")
	private String symbols;
	
	@Autowired
    private ProductRepository productRepository;
	
	private final RestTemplate restTemplate = new RestTemplate();

	public List<ProductCurrencyResponse> getProducts() {

		ProductCurrencyResponse productCurrencyResponse = null;
		List<ProductCurrencyResponse> productCurrencyResponseList = new ArrayList<>();
		Map<String, BigDecimal> rateMap = null;

		List<Product> productList = (List<Product>) productRepository.findAll();

		if(productList.size() > 0) {
			String foreignCurrencyUrl = url.concat(accessKey).concat("&symbols=").concat(symbols);
			ForeignRateResponse foreignRate = restTemplate.getForObject(foreignCurrencyUrl, ForeignRateResponse.class);

			if(foreignRate.isSuccess()){
				for(Product prod: productList) {
					rateMap = new HashMap<>();
					productCurrencyResponse = new ProductCurrencyResponse();
					for(Map.Entry<String, BigDecimal> entry: foreignRate.getRates().entrySet()){
						rateMap.put(entry.getKey(), (prod.getPrice().multiply(entry.getValue())));
					}
					productCurrencyResponse.setId(prod.getId());
					productCurrencyResponse.setName(prod.getName());
					productCurrencyResponse.setDescription(prod.getDescription());
					productCurrencyResponse.setPrice(rateMap);
					productCurrencyResponseList.add(productCurrencyResponse);
				}
			} else {
				throw new CustomInternalServerError(foreignRate.getError().getInfo());
			}
		} else {
				throw new CustomEntityNotFoundException("No products found");
		}
		return productCurrencyResponseList;
    }

	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

	public Product getProduct(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException("No such product found with ID: " + id));
	}
}
