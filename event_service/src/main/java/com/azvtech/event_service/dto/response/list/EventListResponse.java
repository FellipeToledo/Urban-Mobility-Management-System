package com.azvtech.event_service.dto.response.list;

import com.azvtech.event_service.enums.Severity;
import com.azvtech.event_service.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventListResponse {

    private Integer id;
    private String description;
    private Severity severity;
    private Status status;
}
