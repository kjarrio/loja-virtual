package com.github.kjarrio.store.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public class ResponseUtils {

    public static <T> ResponseEntity<T> buildResponse(Optional<T> data) {
        return data.map(
                d -> new ResponseEntity<>(d, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public static <T> ResponseEntity<T> buildResponse(T data) {
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public static ResponseEntity notFoundResponse() {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}