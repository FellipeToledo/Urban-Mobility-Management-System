package com.azvtech.event_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum Severity {
    LOW("Low", 1),
    MEDIUM("Medium", 2),
    HIGH("High", 3),
    CRITICAL("Critical", 4);

    private final String displayName;
    @Getter
    private final int level;

    Severity(String displayName, int level) {
        this.displayName = displayName;
        this.level = level;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Severity fromString(String value) {
        for (Severity severity : Severity.values()) {
            if (severity.name().equalsIgnoreCase(value) || severity.getDisplayName().equalsIgnoreCase(value)) {
                return severity;
            }
        }
        throw new IllegalArgumentException("Invalid Severity: " + value);
    }

    public static Severity fromLevel(int level) {
        for (Severity severity : Severity.values()) {
            if (severity.getLevel() == level) {
                return severity;
            }
        }
        throw new IllegalArgumentException("Invalid Severity level: " + level);
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
