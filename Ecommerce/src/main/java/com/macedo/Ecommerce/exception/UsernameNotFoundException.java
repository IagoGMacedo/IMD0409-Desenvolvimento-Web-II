package com.macedo.Ecommerce.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(){
        super("invalid password");
    }
}
