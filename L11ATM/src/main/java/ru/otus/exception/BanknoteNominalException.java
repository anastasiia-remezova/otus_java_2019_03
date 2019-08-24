package ru.otus.exception;

public class BanknoteNominalException extends RuntimeException {
    public BanknoteNominalException(String message) {
        super(message);
    }
}
