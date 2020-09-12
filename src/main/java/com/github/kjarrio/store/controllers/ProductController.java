package com.github.kjarrio.store.controllers;

import com.github.kjarrio.store.config.Constants;
import com.github.kjarrio.store.models.Product;
import com.github.kjarrio.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constants.PATH_PRODUCTS)
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping(path="/")
    public ResponseEntity<Iterable<Product>> all() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping(path="/")
    public ResponseEntity<Product> add(Product product) {
        return new ResponseEntity<>(repository.save(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> one(@PathVariable Integer id) {
        return repository.findById(id).map(p -> new ResponseEntity<>(p, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}