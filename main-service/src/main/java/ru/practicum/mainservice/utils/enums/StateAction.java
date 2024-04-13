package ru.practicum.mainservice.utils.enums;

import ru.practicum.mainservice.exception.UnknownEnumValueException;

public enum StateAction {
    PUBLISH_EVENT,
    REJECT_EVENT,
    CANCEL_REVIEW,
    SEND_TO_REVIEW;

    public static StateAction getStateActionValue(String stateAction) {
        try {
            return StateAction.valueOf(stateAction.toUpperCase());
        } catch (Exception e) {
            throw new UnknownEnumValueException("Unknown stateAction: " + stateAction);
        }
    }
}
