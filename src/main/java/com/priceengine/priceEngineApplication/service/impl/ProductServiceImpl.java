package com.priceengine.priceEngineApplication.service.impl;

import com.priceengine.priceEngineApplication.entity.Product;
import com.priceengine.priceEngineApplication.repository.ProductRepository;
import com.priceengine.priceEngineApplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> search() {
        return productRepository.findAll();
    }

    @Override
    public Double calculateProductPrice(long id , int units, int cartons) {
        Product product = productRepository.getById(id);
        double unitPrice;
        double price = 0;
        unitPrice=(product.getCartonPrice()+(30/100*product.getCartonPrice()))/product.getUnitsInCarton();
        //carton
        int newCarton = units / product.getUnitsInCarton();
        int remainUnit = units % product.getUnitsInCarton();
        int totalCarton =newCarton+cartons;

        if(totalCarton>3){
            price = (product.getCartonPrice()-(10/100*product.getCartonPrice()))*totalCarton;
        }
        //units
        double totalPrice = unitPrice*remainUnit +price;
        return round(totalPrice,2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
