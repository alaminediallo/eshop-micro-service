package com.lamine.isi.productservice.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}