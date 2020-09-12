package com.github.kjarrio.store.repositories;

import com.github.kjarrio.store.TestUtils;
import com.github.kjarrio.store.models.Product;
import com.github.kjarrio.store.models.Purchase;
import com.github.kjarrio.store.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class PurchaseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private User user;
    private Product product;

    @Before
    public void setUp() throws Exception {

        // Test User
        this.user = TestUtils.createTestUser();
        userRepository.save(this.user);

        // Test Product
        this.product = TestUtils.product();
        entityManager.persist(this.product);

        // Save
        entityManager.flush();

        // Check
        assertEquals(1, productRepository.count());
        assertEquals(1, userRepository.count());

    }

    @Test
    public void testCreatePurchase() {

        Purchase purchase = new Purchase();
        purchase.setUser(userRepository.findById(1).get());
        purchase.setProducts((List<Product>)productRepository.findAll());
        entityManager.persist(purchase);
        entityManager.flush();

        List<Purchase> purchases = (List<Purchase>) purchaseRepository.findAll();
        assertEquals(1, purchases.size());

        // Purchase
        Purchase dbPurchase = purchases.get(0);
        assertEquals(purchase, dbPurchase);

        // User
        assertEquals(this.user, dbPurchase.getUser());

        // Product
        assertEquals(1, dbPurchase.getProducts().size());
        assertEquals(this.product, dbPurchase.getProducts().get(0));

    }

}
