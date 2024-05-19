package com.macedo.Ecommerce.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(){
        super("invalid password");
    }
}
