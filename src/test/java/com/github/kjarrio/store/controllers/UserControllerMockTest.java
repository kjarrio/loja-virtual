package com.github.kjarrio.store.controllers;

import com.github.kjarrio.store.TestUtils;
import com.github.kjarrio.store.config.Constants;
import com.github.kjarrio.store.models.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
@WebMvcTest(UserController.class)
class UserControllerMockTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserController controller;

    @Test
    public void testAddUser() throws Exception {

        User user = TestUtils.createTestUser();

        when(controller.add(any())).thenReturn(new ResponseEntity<>(user, HttpStatus.CREATED));

        mvc.perform(post(Constants.PATH_USERS + "/")
                .content(TestUtils.toJson(user))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(user.getId())))
                .andExpect(jsonPath("name", is(user.getName())))
                .andExpect(jsonPath("email", is(user.getEmail())))
                .andExpect(jsonPath("address", is(user.getAddress())))
                .andExpect(jsonPath("birthDate", is(TestUtils.formatDate(user.getBirthDate()))));

    }


    @Test
    public void testSingleUser() throws Exception {

        User user = TestUtils.createTestUser();

        when(controller.one(any())).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        mvc.perform(get(Constants.PATH_USERS + "/1")
                .with(user("admin").roles("ADMIN"))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(user.getId())))
                .andExpect(jsonPath("name", is(user.getName())))
                .andExpect(jsonPath("email", is(user.getEmail())))
                .andExpect(jsonPath("address", is(user.getAddress())))
                .andExpect(jsonPath("birthDate", is(TestUtils.formatDate(user.getBirthDate()))));

    }

    @Test
    public void testAllUsers() throws Exception {

        User user = TestUtils.createTestUser();

        when(controller.all()).thenReturn(new ResponseEntity<>(Arrays.asList(user), HttpStatus.OK));

        mvc.perform(get(Constants.PATH_USERS + "/")
                .with(user("admin").roles("ADMIN"))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(user.getId())))
                .andExpect(jsonPath("$[0].name", is(user.getName())))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())))
                .andExpect(jsonPath("$[0].address", is(user.getAddress())))
                .andExpect(jsonPath("$[0].birthDate", is(TestUtils.formatDate(user.getBirthDate()))));

    }

}