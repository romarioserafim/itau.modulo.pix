package com.modulo.pix.exception.custom;

public class DataUnprocessableException extends RuntimeException {
    public DataUnprocessableException(String message) {
        super(message);
    }
}