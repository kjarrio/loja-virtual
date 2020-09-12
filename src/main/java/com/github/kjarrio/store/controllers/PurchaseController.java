package com.github.kjarrio.store.controllers;

import com.github.kjarrio.store.config.Constants;
import com.github.kjarrio.store.utils.ResponseUtils;
import com.github.kjarrio.store.repositories.UserRepository;
import com.github.kjarrio.store.repositories.ProductRepository;
import com.github.kjarrio.store.repositories.PurchaseRepository;
import com.github.kjarrio.store.models.Product;
import com.github.kjarrio.store.models.Purchase;
import com.github.kjarrio.store.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = Constants.PATH_PURCHASES)
public class PurchaseController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @PostMapping(path="/")
    public ResponseEntity<Purchase> add(@AuthenticationPrincipal UserDetails user, @RequestParam List<Integer> productIds) {
        Purchase purchase = new Purchase();
        userRepository.findByEmail(user.getUsername()).ifPresent(purchase::setUser);
        productRepository.findAllById(productIds).forEach(purchase::addProduct);
        return new ResponseEntity<>(purchaseRepository.save(purchase), HttpStatus.CREATED);
    }

    @GetMapping(path="/{id}/user")
    public ResponseEntity<User> user(@PathVariable Integer id) {
        return ResponseUtils.buildResponse(userRepository.findById(id));
    }

    @GetMapping(path="/{purchaseId}/products")
    public ResponseEntity<Iterable<Product>> products(@PathVariable Integer purchaseId) {
        return ResponseUtils.buildResponse(purchaseRepository.findById(purchaseId).get().getProducts());
    }

    @PostMapping(path="/{purchaseId}/products")
    public ResponseEntity<Iterable<Product>> addProduct(@PathVariable Integer purchaseId, Integer productId) {
        Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);
        if (!purchase.isPresent()) return ResponseUtils.notFoundResponse();
        productRepository.findById(productId).ifPresent(product -> purchase.get().addProduct(product));
        return ResponseUtils.buildResponse(purchase.get().getProducts());
    }

    @GetMapping(path="/")
    public ResponseEntity<Iterable<Purchase>> all() {
        return ResponseUtils.buildResponse(purchaseRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> one(@PathVariable Integer id) {
        return ResponseUtils.buildResponse(purchaseRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        purchaseRepository.deleteById(id);
    }

}