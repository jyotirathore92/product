package com.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.exception.CustomEntityNotFoundException;
import com.product.exception.CustomInternalServerError;
import com.product.model.ProductCurrencyResponse;
import com.product.repository.ProductRepository;
import com.product.utils.TestDataGen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @TestConfiguration
    static class ProductServiceImplTestContextConfiguration {
        @Bean
        public ProductService productService() {
            return new ProductServiceImpl();
        }
    }

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private final RestTemplate restTemplate = new RestTemplate();

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        ReflectionTestUtils.setField(productService, "url", "http://data.fixer.io/api/latest?access_key=");
        ReflectionTestUtils.setField(productService, "accessKey", "4aaa62511624273d73efb9611030c0cb");
        ReflectionTestUtils.setField(productService, "symbols", "USD");
    }

    @Test
    public void validProduct_shouldReturnEntity() throws URISyntaxException, JsonProcessingException {

        given(productRepository.findAll()).willReturn(TestDataGen.getListOfProduct());

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://data.fixer.io/api/latest?access_key=")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(TestDataGen.getRate()))
                );

        final List<ProductCurrencyResponse> product = productService.getProducts();

        assertEquals(product.get(0).getName(), TestDataGen.getResponse().get(0).getName());
    }

    @Test(expected = CustomEntityNotFoundException.class)
    public void emptyProductListException(){
        given(productRepository.findAll()).willReturn(new ArrayList<>());
        productService.getProducts();
    }

    @Test(expected = CustomInternalServerError.class)
    public void foreignRateFailureException() throws URISyntaxException, JsonProcessingException {
        ReflectionTestUtils.setField(productService, "accessKey", "abcd");
        given(productRepository.findAll()).willReturn(TestDataGen.getListOfProduct());

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://data.fixer.io/api/latest?access_key=")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(TestDataGen.getFailureForForeignRate()))
                );
        productService.getProducts();
    }
}
