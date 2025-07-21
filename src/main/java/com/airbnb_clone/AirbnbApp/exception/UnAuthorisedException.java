package com.airbnb_clone.AirbnbApp.exception;

public class UnAuthorisedException extends RuntimeException {
    public UnAuthorisedException(String message) {
        super(message);
    }
}
