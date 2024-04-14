package ru.practicum.mainservice.utils.enums;

import ru.practicum.mainservice.exception.UnknownEnumValueException;

public enum Status {

    PENDING,
    CONFIRMED,
    CANCELED,
    REJECTED;

    public static Status getStatusValue(String status) {
        try {
            return Status.valueOf(status);
        } catch (Exception e) {
            throw new UnknownEnumValueException("Unknown status: " + status);
        }
    }
}
