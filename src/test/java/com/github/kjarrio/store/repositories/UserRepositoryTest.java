package com.github.kjarrio.store.repositories;

import com.github.kjarrio.store.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void testDuplicateEmails() {

        entityManager.persist(new User("test", "test1@example.com", "test", "test", "test", new Date()));
        entityManager.flush();

        assertThrows(PersistenceException.class, () -> {
            entityManager.persist(new User("test", "test1@example.com", "test", "test", "test", new Date()));
            entityManager.flush();
        });

    }

    @Test
    public void testFindAll() {

        User user1 = new User("test1", "test1@example.com", "test", "test", "test", new Date());
        entityManager.persist(user1);
        entityManager.flush();

        User user2 = new User("test2", "test2@example.com", "test", "test", "test", new Date());
        entityManager.persist(user2);
        entityManager.flush();

        List<User> users = (List<User>)repository.findAll();

        assertEquals(2, users.size());
        assertEquals(user1, users.get(0));
        assertEquals(user2, users.get(1));

    }

    @Test
    public void testSingleUser() {

        User localUser = new User("name", "email@example.com", "password", "telephone", "address", new Date());
        entityManager.persist(localUser);
        entityManager.flush();

        // Only 1 user, and the same information
        List<User> users = (List<User>)repository.findAll();
        assertEquals(1, users.size());

        // Check if user info is the same
        User dbUser = users.get(0);
        assertEquals(localUser, dbUser);

    }

}
