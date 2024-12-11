package com.klp.interview.integration;

public class CountyNotFoundException extends RuntimeException {
    public CountyNotFoundException(String message) {
        super(message);
    }
}
