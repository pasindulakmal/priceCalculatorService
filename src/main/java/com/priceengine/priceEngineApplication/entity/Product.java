package com.priceengine.priceEngineApplication.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Pasindu Lakmal
 */

@Entity
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double cartonPrice;

    private Integer units;

    private Integer unitsInCarton;

    private Double discount;

    private String img;

    public Product(Long id, String name, Double cartonPrice, Integer units, Integer unitsInCarton, Double discount, String img) {
        this.id = id;
        this.name = name;
        this.cartonPrice = cartonPrice;
        this.units = units;
        this.unitsInCarton = unitsInCarton;
        this.discount = discount;
        this.img = img;
    }


}
