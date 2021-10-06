package com.priceengine.priceEngineApplication;

import com.priceengine.priceEngineApplication.entity.Product;
import com.priceengine.priceEngineApplication.repository.ProductRepository;
import com.priceengine.priceEngineApplication.service.ProductService;
import com.priceengine.priceEngineApplication.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;

/**
 * @author Pasindu Lakmal
 */
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class PriceCalculationTest {


    @Nested
    @DisplayName("Price calculation logic testing")
    class WhenCalculatingPrices {

        @InjectMocks
        private ProductService productService = new ProductServiceImpl();

        @MockBean
        private ProductRepository repository;

        @Test
        @DisplayName("purchasing single unit only and less than products in carton ")
        void checkSingleUnitPurchasing() {
            Product product = Product.builder().id(Long.valueOf(1)).cartonPrice(175.00).discount(10.00).unitsInCarton(20).units(1000).build();
            Mockito.when(repository.getById(Long.valueOf(1))).thenReturn(product);
            assertEquals(34.12, productService.calculateProductPrice(1, 3, 0), 0.1);
        }

        @Test
        @DisplayName("purchasing cartons only")
        void checkCartonPurchasing() {
            Product product = Product.builder().id(Long.valueOf(1)).cartonPrice(175.00).discount(10.00).unitsInCarton(20).units(1000).build();
            Mockito.when(repository.getById(Long.valueOf(1))).thenReturn(product);
            assertEquals(350, productService.calculateProductPrice(1, 0, 2), 0.1);
        }

        @Test
        @DisplayName("purchasing single units only and greater than products in carton ")
        void checkSingleUnitPurchasingGraterThanProductsQtyInCarton() {
            Product product = Product.builder().id(Long.valueOf(1)).cartonPrice(175.00).discount(10.00).unitsInCarton(20).units(1000).build();
            Mockito.when(repository.getById(Long.valueOf(1))).thenReturn(product);
            assertEquals(197.74, productService.calculateProductPrice(1, 22, 0), 0.1);
        }

        @Test
        @DisplayName("purchasing units and cartons")
        void checkSingleUnitAndCartonPurchasing() {
            Product product = Product.builder().id(Long.valueOf(1)).cartonPrice(175.00).discount(10.00).unitsInCarton(20).units(1000).build();
            Mockito.when(repository.getById(Long.valueOf(1))).thenReturn(product);
            assertEquals(406.88, productService.calculateProductPrice(1, 5, 2), 0.1);
        }

        @Test
        @DisplayName("purchasing units grater than carton qty and cartons")
        void checkUnitsGraterThanCartonAndCartonPurchasing() {
            Product product = Product.builder().id(Long.valueOf(1)).cartonPrice(175.00).discount(10.00).unitsInCarton(20).units(1000).build();
            Mockito.when(repository.getById(Long.valueOf(1))).thenReturn(product);
            assertEquals(406.88, productService.calculateProductPrice(1, 25, 1), 0.1);
        }

    }
}
