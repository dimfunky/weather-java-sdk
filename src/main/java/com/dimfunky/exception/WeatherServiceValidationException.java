package com.dimfunky.exception;

public class WeatherServiceValidationException extends RuntimeException {
    public WeatherServiceValidationException(String message) {
        super(message);
    }
}
