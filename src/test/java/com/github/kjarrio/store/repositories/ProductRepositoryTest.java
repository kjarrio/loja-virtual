package com.github.kjarrio.store.repositories;

import com.github.kjarrio.store.models.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repository;

    @Test
    public void testProducts() {

        Product product1 = new Product("test1", "test1", BigDecimal.ONE);
        entityManager.persist(product1);
        entityManager.flush();

        Product product2 = new Product("test2", "test2", BigDecimal.ONE);
        entityManager.persist(product2);
        entityManager.flush();

        List<Product> products = (List<Product>)repository.findAll();

        assertEquals(2, products.size());
        assertEquals(product1, products.get(0));
        assertEquals(product2, products.get(1));

    }

    @Test
    public void testSingleProduct() {

        Product localProduct = new Product("test1", "test1", BigDecimal.ONE);
        entityManager.persist(localProduct);
        entityManager.flush();

        // Only 1 user, and the same information
        List<Product> products = (List<Product>) repository.findAll();
        assertEquals(1, products.size());

        // Check if user info is the same
        Product dbProduct = products.get(0);
        assertEquals(localProduct, dbProduct);

    }

}
