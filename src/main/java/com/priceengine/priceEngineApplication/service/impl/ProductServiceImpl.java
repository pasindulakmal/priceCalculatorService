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
    public Double calculateProductPrice(long id, int units, int cartons) {
        Product product = productRepository.getById(id);

        int i = units % product.getUnitsInCarton();


        //set discount
        if(cartons>=3 ||  units / product.getUnitsInCarton()>=3){
            product.setCartonPrice(round(product.getCartonPrice()-((product.getCartonPrice()*product.getDiscount())/100),2));
        }

        //cartons only
        if (units == 0 && cartons != 0) {
            return round(cartons * product.getCartonPrice(),2);
        }

        double unitPrice = (product.getCartonPrice() + ((product.getCartonPrice() * 30) / 100)) / product.getUnitsInCarton();

        //units only
        if (units < product.getUnitsInCarton() && units != 0) {
            return round((unitPrice * units) + (cartons *product.getCartonPrice()),2);
        } else {
            int newCarton = units / product.getUnitsInCarton();
            int remainUnit = units % product.getUnitsInCarton();
            return round(((newCarton+cartons) * product.getCartonPrice()) + (unitPrice * (remainUnit)),2);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
