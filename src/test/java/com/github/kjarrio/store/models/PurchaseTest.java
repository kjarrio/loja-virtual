package com.github.kjarrio.store.models;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseTest {

    private Purchase purchase = new Purchase();

    @Test
    public void setId() {
        purchase.setId(1);
        assertEquals(1, purchase.getId());
    }

    @Test
    public void testUser() {
        User user = new User();
        purchase.setUser(user);
        assertEquals(user.hashCode(), purchase.getUser().hashCode());
    }

    @Test
    public void testProducts() {
        Purchase purchase = new Purchase();
        purchase.setProducts(Collections.singletonList(new Product()));
        assertEquals(1, purchase.getProducts().size());
    }

    @Test
    public void testAddProduct() {
        purchase.addProduct(new Product("name", "desc", BigDecimal.TEN));
        assertEquals(1, purchase.getProducts().size());
    }

    @Test
    public void testTotal() {
        purchase.setProducts(Arrays.asList(new Product("name", "desc", BigDecimal.TEN), new Product("name", "desc", BigDecimal.TEN)));
        assertEquals(new BigDecimal("20"), purchase.getTotal());
    }

}