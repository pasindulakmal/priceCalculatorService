package com.priceengine.priceEngineApplication.response;

import lombok.Data;

@Data
public class ProductSearchResponse {

    private int id;

    private String name;

    private Double cartonPrice;

    private Integer units;

    private Integer unitsInCarton;

    private Double discount;

}
