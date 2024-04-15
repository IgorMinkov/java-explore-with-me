package ru.practicum.exception;

public class UnknownEnumValueException extends RuntimeException {
    public UnknownEnumValueException(String message) {
        super(message);
    }
}
