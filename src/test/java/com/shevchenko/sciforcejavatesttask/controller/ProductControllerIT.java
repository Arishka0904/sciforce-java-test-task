package com.shevchenko.sciforcejavatesttask.controller;

import com.shevchenko.sciforcejavatesttask.entity.Product;
import com.shevchenko.sciforcejavatesttask.service.ProductServiceImplementation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class ProductControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductServiceImplementation productService;

    private List<Product> productsTest1 = new ArrayList<>();
    private static final Product product1 = new Product();
    private static final Product product2 = new Product();


    @Test
    @Sql(value = {"/create-productsTable-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldSaveAllProductsFromLocale() throws Exception {
        setUpForCheckEmptyDb();
        this.mockMvc.perform(post("/json/locale"))
                .andDo(print())
                .andExpect(status().isCreated());

        Assert.assertEquals(productsTest1, productService.getAllProducts());
    }

    @Test
    @Sql(value = {"/create-product-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldAddAmountAndSaveNewFromInternet() throws Exception {
        setUpForCheckDbWithProducts();
        this.mockMvc.perform(post("/json/internet"))
                .andDo(print())
                .andExpect(status().isCreated());

        Assert.assertEquals(productsTest1, productService.getAllProducts());
    }

    @Test
    @Sql(value = {"/create-productsTable-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldSaveAllProductsFromInternet() throws Exception {
        setUpForCheckEmptyDb();
        this.mockMvc.perform(post("/json/internet"))
                .andDo(print())
                .andExpect(status().isCreated());

        Assert.assertEquals(productsTest1, productService.getAllProducts());
    }

    @Test
    @Sql(value = {"/create-product-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldAddAmountAndSaveNewFromLocale() throws Exception {
        setUpForCheckDbWithProducts();
        this.mockMvc.perform(post("/json/locale"))
                .andDo(print())
                .andExpect(status().isCreated());

        Assert.assertEquals(productsTest1, productService.getAllProducts());
    }

    private void setUpForCheckEmptyDb() {
        product1.setId(1l);
        product1.setProductName("Product1");
        product1.setAmount(1);
        product1.setProductUuid("1-1-1");
        product2.setId(2l);
        product2.setProductName("Product2");
        product2.setAmount(2);
        product2.setProductUuid("2-2-2");

        productsTest1.add(product1);
        productsTest1.add(product2);
    }

    private void setUpForCheckDbWithProducts() {
        product1.setId(1l);
        product1.setProductName("Product1");
        product1.setAmount(16);
        product1.setProductUuid("1-1-1");
        product2.setId(2l);
        product2.setProductName("Product2");
        product2.setAmount(2);
        product2.setProductUuid("2-2-2");

        productsTest1.add(product1);
        productsTest1.add(product2);
    }
}
