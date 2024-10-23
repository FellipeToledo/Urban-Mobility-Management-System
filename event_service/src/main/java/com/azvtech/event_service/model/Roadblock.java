package com.azvtech.event_service.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Embeddable
public class Roadblock {

    private String road;

    private String startRoad;

    private String endRoad;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

}
