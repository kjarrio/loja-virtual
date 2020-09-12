package com.github.kjarrio.store.repositories;

import com.github.kjarrio.store.models.User;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}