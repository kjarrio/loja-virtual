package com.github.kjarrio.store.controllers;

import com.github.kjarrio.store.TestUtils;
import com.github.kjarrio.store.config.Constants;
import com.github.kjarrio.store.models.Product;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
class ProductControllerMockTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductController controller;

    @Test
    public void testAddProduct() throws Exception {

        Product product = TestUtils.product();

        when(controller.add(any())).thenReturn(new ResponseEntity<>(product, HttpStatus.CREATED));

        mvc.perform(post(Constants.PATH_PRODUCTS + "/")
                .with(user("admin").roles("ADMIN"))
                .content(TestUtils.toJson(product))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(product.getId())))
                .andExpect(jsonPath("name", is(product.getName())))
                .andExpect(jsonPath("description", is(product.getDescription())))
                .andExpect(jsonPath("price", is(product.getPrice().intValue())));

    }


    @Test
    public void testSingleProduct() throws Exception {

        Product product = TestUtils.product();

        when(controller.one(any())).thenReturn(new ResponseEntity<>(product, HttpStatus.OK));

        mvc.perform(get(Constants.PATH_PRODUCTS + "/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(product.getId())))
                .andExpect(jsonPath("name", is(product.getName())))
                .andExpect(jsonPath("description", is(product.getDescription())))
                .andExpect(jsonPath("price", is(product.getPrice().intValue())));

    }


    @Test
    public void testAllProducts() throws Exception {

        Product product = TestUtils.product();

        when(controller.all()).thenReturn(new ResponseEntity<>(Arrays.asList(product), HttpStatus.OK));

        mvc.perform(get(Constants.PATH_PRODUCTS + "/")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(product.getId())))
                .andExpect(jsonPath("$[0].name", is(product.getName())))
                .andExpect(jsonPath("$[0].description", is(product.getDescription())))
                .andExpect(jsonPath("$[0].price", is(product.getPrice().intValue())));

    }


}