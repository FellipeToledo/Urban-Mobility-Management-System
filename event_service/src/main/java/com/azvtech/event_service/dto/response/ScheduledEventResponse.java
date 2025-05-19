package com.azvtech.event_service.dto.response;

import com.azvtech.event_service.dto.response.detail.EventDetailResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduledEventResponse extends EventDetailResponse {
    private Integer regulationNumber;
    private String regulationPublicationDate;
}
