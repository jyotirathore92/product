package com.product.controller;

import com.product.ProductApplication;
import com.product.entity.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void validProduct_shouldReturnWith200() {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<?> response = restTemplate.exchange(createURLWithPort("/products"), HttpMethod.GET, entity, String.class);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void validPOST_shouldSucceed() {
        Product product = new Product();
        product.setName("Apple");
        product.setDescription("iPhone 13 Pro");
        product.setPrice(new BigDecimal("500"));


        HttpEntity<Product> entity = new HttpEntity<>(product, headers);

        ResponseEntity<?> response = restTemplate.exchange(createURLWithPort("/products"), HttpMethod.POST, entity, Product.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    public void validProductID_shouldReturnWith200() {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<?> response = restTemplate.exchange(createURLWithPort("/products/1"), HttpMethod.GET, entity, String.class);

        String expected = "{\"id\":1,\"name\":\"Apple\",\"description\":\"iPhone 13 Pro\",\"price\":1200.00}";

        assertEquals(expected, response.getBody());
        assertEquals(200, response.getStatusCode().value());
    }

    private URI createURLWithPort(String uri) {
        return URI.create("http://localhost:" + port + uri);
    }

}
