package com.github.kjarrio.store.models;

import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    private User user = new User();

    @Test
    public void testId() {
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    public void testName() {
        user.setName("test");
        assertEquals("test", user.getName());
    }

    @Test
    public void testEmail() {
        user.setEmail("test");
        assertEquals("test", user.getEmail());
    }

    @Test
    public void testPassword() {
        user.setPassword("test");
        assertEquals("test", user.getPassword());
    }

    @Test
    public void testTelephone() {
        user.setTelephone("test");
        assertEquals("test", user.getTelephone());
    }

    @Test
    public void testAddress() {
        user.setAddress("test");
        assertEquals("test", user.getAddress());
    }

    @Test
    public void testBirthDate() {
        user.setBirthDate(new Date(12345));
        assertEquals(new Date(12345), user.getBirthDate());
    }

}