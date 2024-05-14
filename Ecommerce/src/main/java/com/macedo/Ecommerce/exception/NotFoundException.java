package com.macedo.Ecommerce.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String object) {
        super("%s not found".formatted(object));
    }
}
