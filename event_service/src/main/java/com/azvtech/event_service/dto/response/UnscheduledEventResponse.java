package com.azvtech.event_service.dto.response;

import com.azvtech.event_service.dto.response.detail.EventDetailResponse;
import com.azvtech.event_service.enums.Cause;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnscheduledEventResponse extends EventDetailResponse {
    private Cause cause;
}
