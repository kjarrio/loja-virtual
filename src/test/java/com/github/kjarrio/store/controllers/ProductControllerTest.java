package com.github.kjarrio.store.controllers;

import com.github.kjarrio.store.TestUtils;
import com.github.kjarrio.store.models.Product;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController controller;

    @Test
    public void testProductController() {

        clear();

        // Test Products
        Product product1 = TestUtils.product("test1");
        Product product2 = TestUtils.product("test2");
        controller.add(product1);
        controller.add(product2);

        List<Product> products = getAllProducts();

        // Check
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(product1, products.get(0));
        assertEquals(product2, products.get(1));

        // Single user
        Product singleProduct = controller.one(product1.getId()).getBody();
        assertNotNull(singleProduct);
        assertEquals(singleProduct, product1);

        // Delete
        controller.delete(product1.getId());
        products = (List<Product>)controller.all().getBody();
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(product2, products.get(0));

    }

    private void clear() {
        List<Product> products = getAllProducts();
        for (Product p : products) controller.delete(p.getId());
    }

    private List<Product> getAllProducts() {
        return (List<Product>)controller.all().getBody();
    }

}