package com.github.kjarrio.store.repositories;

import com.github.kjarrio.store.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}