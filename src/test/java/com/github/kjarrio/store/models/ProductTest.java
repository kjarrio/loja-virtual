package com.github.kjarrio.store.models;

import com.github.kjarrio.store.TestUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    private Product product = TestUtils.product();

    @Test
    public void testId() {
        product.setId(1);
        assertEquals(1, product.getId());
    }

    @Test
    public void testName() {
        product.setName("test");
        assertEquals("test", product.getName());
    }

    @Test
    public void testDescription() {
        product.setDescription("test");
        assertEquals("test", product.getDescription());
    }

    @Test
    public void testPrice() {
        product.setPrice(BigDecimal.ONE);
        assertEquals(BigDecimal.ONE, product.getPrice());
    }

}