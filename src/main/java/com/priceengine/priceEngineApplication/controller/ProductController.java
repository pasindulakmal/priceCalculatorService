package com.priceengine.priceEngineApplication.controller;

import com.priceengine.priceEngineApplication.entity.Product;
import com.priceengine.priceEngineApplication.request.ProductCreateRequest;
import com.priceengine.priceEngineApplication.request.ProductPriceRequest;
import com.priceengine.priceEngineApplication.response.ProductCreateResponse;
import com.priceengine.priceEngineApplication.response.ProductPriceResponse;
import com.priceengine.priceEngineApplication.response.ProductSearchResponse;
import com.priceengine.priceEngineApplication.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pasindu Lakmal
 */
@Controller
public class ProductController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductService productService;

    @PostMapping("${app.endpoint.productCreate}")
    public ResponseEntity<Object> saveProduct(@Validated @RequestBody ProductCreateRequest request) {
        Product product = modelMapper.map(request, Product.class);
        Product savedProduct = productService.save(product);
        ProductCreateResponse productCreateResponse = modelMapper.map(savedProduct, ProductCreateResponse.class);
        return new ResponseEntity<>(productCreateResponse, HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("${app.endpoint.productSearch}")
    public ResponseEntity<List<ProductSearchResponse>> search() {
        List<Product> productList = productService.search();
        List<ProductSearchResponse> productSearchResponses = productList.stream().map(product ->
                modelMapper.map(product, ProductSearchResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(productSearchResponses, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping("${app.endpoint.productPriceView}")
    public ResponseEntity<ProductPriceResponse> viewPrice(@PathVariable long id, ProductPriceRequest request) {
        Double totalPrice = productService.calculateProductPrice(id, request.getUnits(), request.getCartons());
        ProductPriceResponse productPriceResponse =  new ProductPriceResponse();
        productPriceResponse.setPrice(totalPrice);
        return new ResponseEntity<>(productPriceResponse, new HttpHeaders(), HttpStatus.OK);
    }
}
