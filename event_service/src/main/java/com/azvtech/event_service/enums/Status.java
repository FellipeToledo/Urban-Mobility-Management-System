package com.azvtech.event_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    PLANNING("Planejamento"),
    ACTIVE("Ativo"),
    SUSPENDED("Suspenso"),
    IN_ANALYSIS("Em Análise"),
    UNCONFIRMED("Não Confirmado"),
    MONITORING("Monitoramento"),
    CLOSED("Encerrado"),
    CANCELED("Cancelado");

    private final String displayName;
    private static final Map<String, Status> LOOKUP_MAP = new HashMap<>();

    static {
        for (Status status : values()) {
            LOOKUP_MAP.put(status.name().toLowerCase(), status);
            LOOKUP_MAP.put(status.displayName.toLowerCase(), status);
        }
    }

    Status(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Status fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Status value cannot be null");
        }
        Status status = LOOKUP_MAP.get(value.toLowerCase());
        if (status == null) {
            throw new IllegalArgumentException("Invalid Status: " + value);
        }
        return status;
    }
}
