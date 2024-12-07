package com.azvtech.event_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum Severity {
    UNDEFINED("Indefinida", 0),
    LOW("Leve",1),
    MEDIUM("Moderada",2),
    HIGH("Grave",3),
    CRITICAL("Crítica",4);

    private final String displayName;
    @Getter
    private final int level;
    private static final Map<String, Severity> LOOKUP_MAP = new HashMap<>();

    static {
        for (Severity severity : values()) {
            LOOKUP_MAP.put(severity.name().toLowerCase(), severity);
            LOOKUP_MAP.put(severity.displayName.toLowerCase(), severity);
        }
    }

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
        if (value == null) {
            throw new IllegalArgumentException("Severity value cannot be null");
        }

        Severity severity = LOOKUP_MAP.get(value.toLowerCase());
        if (severity == null) {
            throw new IllegalArgumentException("Invalid Severity: " + value);
        }
        return severity;
    }

    // Optional: Add method to find by level if needed
    public static Severity fromLevel(int level) {
        for (Severity severity : values()) {
            if (severity.getLevel() == level) {
                return severity;
            }
        }
        throw new IllegalArgumentException("Invalid Severity level: " + level);
    }
}
