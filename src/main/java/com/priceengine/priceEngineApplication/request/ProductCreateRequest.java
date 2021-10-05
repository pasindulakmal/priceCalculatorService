package com.priceengine.priceEngineApplication.request;

import lombok.Data;

/**
 * @author Pasindu Lakmal
 */
@Data
public class ProductCreateRequest {

    private String name;

    private Double cartonPrice;

    private Integer units;

    private Integer unitsInCarton;

    private Double discount;

    private String img;
}
