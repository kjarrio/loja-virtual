package com.github.kjarrio.store;

import com.github.kjarrio.store.models.Product;
import com.github.kjarrio.store.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static byte[] toJson(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsBytes(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static User createTestUser() {
        return createTestUser("user@user.com");
    }

    public static User createTestUser(String email) {
        User user = new User();
        user.setName("test");
        user.setEmail(email);
        user.setTelephone("123123");
        user.setAddress("123123");
        user.setBirthDate(new Date(1980, 1, 1));
        user.setPassword("password");
        return user;
    }

    public static Product product() {
        return product("test");
    }

    public static Product product(String name) {
        Product product = new Product();
        product.setName(name);
        product.setDescription("test");
        product.setPrice(BigDecimal.ONE);
        return product;
    }


}