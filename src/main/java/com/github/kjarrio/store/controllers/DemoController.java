package com.github.kjarrio.store.controllers;

import com.github.kjarrio.store.config.Constants;
import com.github.kjarrio.store.models.Product;
import com.github.kjarrio.store.models.Purchase;
import com.github.kjarrio.store.models.User;
import com.github.kjarrio.store.repositories.ProductRepository;
import com.github.kjarrio.store.repositories.PurchaseRepository;
import com.github.kjarrio.store.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@Controller
@ApiIgnore
public class DemoController implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    Environment environment;

    @GetMapping(path="/")
    @PreAuthorize("hasRole('ADMIN')")
    public String intro() {
        return "index";
    }

    @GetMapping(path="/postman.json", produces=MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> postman(HttpServletResponse response, @CookieValue(name = "JSESSIONID") String sessionId) {
        try {
            String jsonContents = new String(Files.readAllBytes(new File(Constants.JSON_FOLDER, "postman.json").toPath()));
            response.setHeader("Content-Disposition","attachment; filename=postman.json");
            return new ResponseEntity<>(jsonContents.replace("%%SESSION%%", sessionId), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        // This data is for demonstration of the API, not for use during unit tests...
        if (Arrays.asList(this.environment.getActiveProfiles()).contains("test")) return;

        // Wait a little for the database to start
        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();

        // Products
        try {
            List<Product> products = mapper.readValue(new File(Constants.JSON_FOLDER, "products.json"), new TypeReference<List<Product>>(){});
            productRepository.saveAll(products);
        } catch (Exception e) { return; }

        // Users
        try {
            List<User> users = mapper.readValue(new File(Constants.JSON_FOLDER, "users.json"), new TypeReference<List<User>>(){});
            for (User user : users) user.setPassword(passwordEncoder.encode(Constants.DEFAULT_PASSWORD));
            userRepository.saveAll(users);
        } catch (Exception e) { return; }

        // Sample Purchase
        Purchase purchase = new Purchase();
        purchase.setUser(userRepository.findAll().iterator().next());
        productRepository.findAll().forEach(purchase::addProduct);
        purchaseRepository.save(purchase);

    }

}