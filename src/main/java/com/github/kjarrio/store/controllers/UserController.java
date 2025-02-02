package com.github.kjarrio.store.controllers;

import com.github.kjarrio.store.config.Constants;
import com.github.kjarrio.store.utils.ResponseUtils;
import com.github.kjarrio.store.models.User;
import com.github.kjarrio.store.repositories.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constants.PATH_USERS)
@Api(value = "Users", tags = "Users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "Get all users")
    @GetMapping(path = "/")
    public ResponseEntity<Iterable<User>> all() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Add a user")
    @PostMapping(path="/")
    public ResponseEntity<User> add(@Validated User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(userRepository);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get single user")
    @GetMapping("/{id}")
    public ResponseEntity<User> one(@PathVariable Integer id) {
        return ResponseUtils.buildResponse(userRepository.findById(id));
    }

    @ApiOperation(value = "Delete user")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

}