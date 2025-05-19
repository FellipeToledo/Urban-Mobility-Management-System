package com.azvtech.event_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RoadblockResponse {
    private String road;
    private String startRoad;
    private String endRoad;
    private String startDateTime;
    private String endDateTime;
}
