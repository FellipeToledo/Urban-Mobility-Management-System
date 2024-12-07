package com.azvtech.event_service.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Embeddable
public class Roadblock {

    @NotNull(message = "Road cannot be null")
    private String road;

    @NotNull(message = "Road cannot be null")
    private String startRoad;

    @NotNull(message = "Road cannot be null")
    private String endRoad;

    @NotNull(message = "Road cannot be null")
    private LocalDateTime startDateTime;

    @NotNull(message = "Road cannot be null")
    private LocalDateTime endDateTime;
}
