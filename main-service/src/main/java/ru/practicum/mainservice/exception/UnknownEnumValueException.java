package ru.practicum.mainservice.exception;

public class UnknownEnumValueException extends RuntimeException {
    public UnknownEnumValueException(String message) {
        super(message);
    }
}