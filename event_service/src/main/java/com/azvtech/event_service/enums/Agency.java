package com.azvtech.event_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Agency {
    CET_RIO("Companhia de Engenharia de Tráfego"),
    SECONSERVA("Secretaria Municipal de Conservação"),
    GM_RIO("Guarda Municipal do Rio de Janeiro"),
    COMLURB("Companhia Municipal de Limpeza Urbana"),
    PMERJ("Polícia Militar do Estado do Rio de Janeiro"),
    OTHER("Outro");

    private final String displayName;
    private static final Map<String, Agency> LOOKUP_MAP = new HashMap<>();

    static {
        for (Agency agency : values()) {
            LOOKUP_MAP.put(agency.name().toLowerCase(), agency);
            LOOKUP_MAP.put(agency.displayName.toLowerCase(), agency);
        }
    }

    Agency(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Agency fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Agency value cannot be null");
        }

        Agency agency = LOOKUP_MAP.get(value.toLowerCase());
        if (agency == null) {
            throw new IllegalArgumentException("Invalid Agency: " + value);
        }
        return agency;
    }
}
