package com.eon.in.products.service;

import com.eon.in.products.model.CreateProductRestModel;

import java.util.concurrent.ExecutionException;

public interface ProductService {

    public String createProduct(CreateProductRestModel createProductModel) throws ExecutionException, InterruptedException;

}
