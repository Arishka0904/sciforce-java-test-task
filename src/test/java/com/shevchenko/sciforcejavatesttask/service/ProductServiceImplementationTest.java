package com.shevchenko.sciforcejavatesttask.service;

import com.shevchenko.sciforcejavatesttask.entity.Product;
import com.shevchenko.sciforcejavatesttask.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceImplementationTest {
    private ProductRepository productRepo = mock(ProductRepository.class);
    private JsonExchangeClient exchangeClient = mock(JsonExchangeClient.class);
    private List<Product> productsTest1 = new ArrayList<>();
    private List<Product> productsTest2 = new ArrayList<>();

    private ProductServiceImplementation productService = new ProductServiceImplementation(exchangeClient, productRepo);

    private static final Product product1 = new Product();
    private static final Product product2 = new Product();
    private static final Product product3 = new Product();


    @BeforeEach
    public void setUp() {
        product1.setId(1l);
        product1.setProductName("Product1");
        product1.setAmount(1);
        product1.setProductUuid("1-1");
        product2.setId(2l);
        product2.setProductName("Product2");
        product2.setAmount(2);
        product2.setProductUuid("2-2");
        product3.setId(3l);
        product3.setProductName("Product3");
        product3.setAmount(3);
        product3.setProductUuid("3-3");

        productsTest1.add(product1);
        productsTest1.add(product2);
        productsTest2.add(product2);
        productsTest2.add(product3);

    }

    @Test
    public void shouldSaveAllProductsFromInternet() {

        when(productRepo.findAll()).thenReturn(Collections.emptyList());
        when(exchangeClient.getProductsFromInternetFile(any())).thenReturn(productsTest1);

        productService.saveListOfProductsFromInternetFile();
        verify(productRepo, times(1)).saveAll(productsTest1);
    }

    @Test
    public void shouldSaveNewOrUpdateProductsFromInternet() {

        when(productRepo.findAll()).thenReturn(productsTest1);
        when(exchangeClient.getProductsFromInternetFile(any())).thenReturn(productsTest2);

        productService.saveListOfProductsFromInternetFile();

        verify(productRepo, times(1)).findByProductUuid("2-2");
        verify(productRepo, times(1)).findByProductUuid("3-3");
        verify(productRepo, times(1)).save(product2);
        verify(productRepo, times(1)).save(product3);
    }

    @Test
    public void shouldSaveAllProductsFromLocale() {

        when(productRepo.findAll()).thenReturn(Collections.emptyList());
        when(exchangeClient.getProductsLocaleFile(any())).thenReturn(productsTest1);

        productService.saveListOfProductsFromLocaleFile();
        verify(productRepo, times(1)).saveAll(productsTest1);
    }

    @Test
    public void shouldReturnTrueIfProductExist() {

        when(productRepo.findAll()).thenReturn(productsTest1);
        assertTrue(productService.isProductsExist());
    }
}
