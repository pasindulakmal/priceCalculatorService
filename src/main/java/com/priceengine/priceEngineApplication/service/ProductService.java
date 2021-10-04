package com.priceengine.priceEngineApplication.service;

import com.priceengine.priceEngineApplication.entity.Product;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
public interface ProductService {

    Product save(Product product);

    List<Product> search();

    Double calculateProductPrice(long id , int units, int cartons);
}
