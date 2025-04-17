package com.azvtech.event_service.dto.response.detail;

import com.azvtech.event_service.dto.response.RoadblockResponse;
import com.azvtech.event_service.dto.response.list.EventListResponse;
import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventDetailResponse extends EventListResponse {

    private String neighborhood;
    @ElementCollection
    private List<RoadblockResponse> roadblocks;
}
