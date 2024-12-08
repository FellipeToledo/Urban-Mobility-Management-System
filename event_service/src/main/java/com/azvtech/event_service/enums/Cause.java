package com.azvtech.event_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the causes of events in the urban mobility system.
 * @author Fellipe Toledo
 */

public enum Cause {
    ACCIDENT("Acidente"),
    CONSTRUCTION("Obra"),
    WEATHER("Condição Climática"),
    DEMONSTRATION("Manifestação"),
    BREAKDOWN("Falha Técnica"),
    OTHER("Outro");

    private final String displayName;
    private static final Map<String, Cause> LOOKUP_MAP = new HashMap<>();

    static {
        for (Cause cause : values()) {
            LOOKUP_MAP.put(cause.name().toLowerCase(), cause);
            LOOKUP_MAP.put(cause.displayName.toLowerCase(), cause);
        }
    }

    Cause(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Cause fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Cause value cannot be null");
        }

        Cause cause = LOOKUP_MAP.get(value.toLowerCase());
        if (cause == null) {
            throw new IllegalArgumentException("Invalid Cause: " + value);
        }
        return cause;
    }
}

