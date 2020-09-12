package com.github.kjarrio.store.repositories;

import com.github.kjarrio.store.models.Purchase;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {

}