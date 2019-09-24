package com.shevchenko.sciforcejavatesttask.service;

import com.shevchenko.sciforcejavatesttask.entity.Product;
import com.shevchenko.sciforcejavatesttask.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ProductServiceImplementation implements ProductService {

    private JsonExchangeClient exchangeClient;
    private ProductRepository productRepository;

    @Value("${pathToLocalFile}")
    private String pathToLocalFile;

    @Value("${pathToFileFromInternet}")
    private String pathToFileFromInternet;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImplementation.class);

    public ProductServiceImplementation(JsonExchangeClient exchangeClient, ProductRepository productRepository) {
        this.exchangeClient = exchangeClient;
        this.productRepository = productRepository;
    }

    @Override
    public void saveListOfProductsFromInternetFile() {

        List<Product> products = exchangeClient.getProductsFromInternetFile(pathToFileFromInternet);

        saveNewOrAddAmount(products, "internet");
    }

    @Override
    public void saveListOfProductsFromLocaleFile() {
        List<Product> products = exchangeClient.getProductsLocaleFile(pathToLocalFile);

        saveNewOrAddAmount(products, "locale");
    }

    @Transactional(readOnly = true)
    public boolean isProductsExist() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    private void saveNewOrAddAmount(List<Product> products, String resource) {
        int countNew = 0;
        if (!isProductsExist()) {
            productRepository.saveAll(products);
            countNew = products.size();
            logger.info(countNew + " new products from " + resource + " file are saved");
        } else {
            int countUpdate = 0;
            for (Product product : products) {
                Optional<Product> productByUuid = productRepository.findByProductUuid(product.getProductUuid());
                if (productByUuid.isPresent()) {
                    Product productFromDb = productByUuid.get();
                    productFromDb.setAmount(productFromDb.getAmount() + product.getAmount());
                    productRepository.save(productFromDb);
                    countUpdate++;
                } else {
                    productRepository.save(product);
                    countNew++;
                }
            }
            logger.info(countUpdate + " products from " + resource + " file was updated");
            logger.info(countNew + " new products from " + resource + " file are saved");
        }
    }
}
