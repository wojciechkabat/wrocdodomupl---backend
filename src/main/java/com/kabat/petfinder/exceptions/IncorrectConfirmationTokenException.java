package com.kabat.petfinder.exceptions;

public class IncorrectConfirmationTokenException extends RuntimeException {
    public IncorrectConfirmationTokenException(String message) {
        super(message);
    }
}
