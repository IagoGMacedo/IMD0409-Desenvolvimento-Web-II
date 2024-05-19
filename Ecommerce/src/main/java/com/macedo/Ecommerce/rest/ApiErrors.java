package com.macedo.Ecommerce.rest;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ApiErrors {
    @Getter
    private List<String> errors;

    public ApiErrors(String errorMessage){
        this.errors = Arrays.asList(errorMessage);
    }

    public ApiErrors(List<String> errorMessages){
        this.errors = errorMessages;
    }
}
