package com.macedo.Ecommerce.rest.controllers;

import java.util.stream.Collectors;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.macedo.Ecommerce.exception.PatchErrorException;
import com.macedo.Ecommerce.exception.BusinessRuleException;
import com.macedo.Ecommerce.exception.InvalidPasswordException;
import com.macedo.Ecommerce.exception.NotFoundException;
import com.macedo.Ecommerce.rest.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrors> handleNotFoundException(NotFoundException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<ApiErrors>(new ApiErrors(errorMessage), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiErrors> handleNotFoundException(BusinessRuleException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<ApiErrors>(new ApiErrors(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatchErrorException.class)
    public ResponseEntity<ApiErrors> handleNotFoundException(PatchErrorException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<ApiErrors>(new ApiErrors(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiErrors> handleNotFoundException(InvalidPasswordException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<ApiErrors>(new ApiErrors(errorMessage), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(errors);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrors> handleMethodNotValidException(ResponseStatusException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<ApiErrors>(new ApiErrors(errorMessage), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrors> handleMethodNotValidException(IllegalArgumentException ex) {
        return new ResponseEntity<ApiErrors>(new ApiErrors("{campo.role.invalido}"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrors> handleMethodNotValidException(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();
        String formattedMessage = formatMessage(message);

        return new ResponseEntity<>(new ApiErrors(formattedMessage), HttpStatus.CONFLICT);
    }

    private String formatMessage(String message) {
        // Regex para capturar o nome do campo que causou a violação de unicidade
        Pattern pattern = Pattern.compile("\\(([^)]+)\\)=\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String fieldName = matcher.group(1).trim();
            String fieldValue = matcher.group(2).trim();
            return "O campo '" + fieldName + "' com valor '" + fieldValue + "' já existe.";
        }

        return "Violação de integridade de dados.";
    }

}
