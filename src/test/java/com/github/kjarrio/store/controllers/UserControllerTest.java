package com.github.kjarrio.store.controllers;

import com.github.kjarrio.store.TestUtils;
import com.github.kjarrio.store.models.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController controller;

    @Test
    public void testUserController() {

        // Test Users
        User user1 = TestUtils.createTestUser("test1@test.com");
        controller.add(user1);

        User user2 = TestUtils.createTestUser("test2@test.com");
        controller.add(user2);

        List<User> users = getAllUsers();

        // Check
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(user1, users.get(0));
        assertEquals(user2, users.get(1));

        // Single user
        User singleUser = controller.one(user1.getId()).getBody();
        assertNotNull(singleUser);
        assertEquals(singleUser, user1);

        // Delete
        controller.delete(user1.getId());
        users = (List<User>)controller.all().getBody();
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user2, users.get(0));

    }

    private List<User> getAllUsers() {
        return (List<User>)controller.all().getBody();
    }

}