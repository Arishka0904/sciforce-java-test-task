package com.shevchenko.sciforcejavatesttask.controller;

import com.shevchenko.sciforcejavatesttask.entity.Product;
import com.shevchenko.sciforcejavatesttask.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("json")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/locale")
    public void saveJsonFromLocaleFile() {
        productService.saveListOfProductsFromLocaleFile();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/internet")
    public void saveJsonFromInternet() {

        productService.saveListOfProductsFromInternetFile();
    }

    @GetMapping("/getAll")
    public List<Product> getAllProduct(){
        return productService.getAllProducts();
    }
}
