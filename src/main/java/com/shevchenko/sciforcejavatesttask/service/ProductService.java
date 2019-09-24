package com.shevchenko.sciforcejavatesttask.service;

import com.shevchenko.sciforcejavatesttask.entity.Product;

import java.util.List;

public interface ProductService {
    public void saveListOfProductsFromInternetFile();

    public void saveListOfProductsFromLocaleFile();

    public boolean isProductsExist();

    public List<Product> getAllProducts();
}
