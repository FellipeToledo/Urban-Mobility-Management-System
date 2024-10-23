package com.azvtech.event_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    PENDING("Pending"),
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    CLOSED("Closed"),
    CANCELED("Canceled");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Status fromString(String value) {
        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(value) || status.getDisplayName().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Status: " + value);
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
