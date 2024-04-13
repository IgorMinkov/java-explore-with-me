package ru.practicum.mainservice.utils.enums;

import ru.practicum.mainservice.exception.UnknownEnumValueException;

public enum State {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static State getStateValue(String state) {
        try {
            return State.valueOf(state.toUpperCase());
        } catch (Exception e) {
            throw new UnknownEnumValueException("Unknown state: " + state);
        }
    }
}
