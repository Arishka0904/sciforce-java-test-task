package com.shevchenko.sciforcejavatesttask.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevchenko.sciforcejavatesttask.controller.ProductController;
import com.shevchenko.sciforcejavatesttask.entity.Product;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Component
public class JsonExchangeClient {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private RestTemplate restTemplate;

    public List<Product> getProductsFromInternetFile(String url) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        restTemplate = new RestTemplate(requestFactory);
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
                });
        return response.getBody();
    }

    public List<Product> getProductsLocaleFile(String url) {

        File jsonFile = null;
        List<Product> products = null;
        try {
            jsonFile = ResourceUtils.getFile(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            products = objectMapper.readValue(jsonFile,
                    new TypeReference<List<Product>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}
