package com.eon.in.products.controller;

import com.eon.in.products.error.ErrorMessage;
import com.eon.in.products.model.CreateProductRestModel;
import com.eon.in.products.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/products")  // Base URL for all product-related endpoints
public class ProductController {

    ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Define endpoints for product operations (e.g., create, read, update, delete)
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductRestModel product) {
        // Logic to create a new product
        String productId;
        try {
            productId = productService.createProduct(product);

        } catch (Exception e) {
            // e.printStackTrace();
            LOGGER.error("Error creating product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(new Date(), "Failed to create product", e.getMessage()).toString());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
