package com.azvtech.event_service.dto.mapper;

import com.azvtech.event_service.dto.request.EventRequest;
import com.azvtech.event_service.dto.request.ScheduledEventRequest;
import com.azvtech.event_service.dto.request.UnscheduledEventRequest;
import com.azvtech.event_service.dto.response.RoadblockResponse;
import com.azvtech.event_service.dto.response.ScheduledEventResponse;
import com.azvtech.event_service.dto.response.UnscheduledEventResponse;
import com.azvtech.event_service.dto.response.detail.EventDetailResponse;
import com.azvtech.event_service.dto.response.list.EventListResponse;
import com.azvtech.event_service.model.Event;
import com.azvtech.event_service.model.Roadblock;
import com.azvtech.event_service.model.ScheduledEvent;
import com.azvtech.event_service.model.UnscheduledEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    //Listing (Entity → List DTO)
    EventListResponse toEventListResponse(Event event);

    // Details (Entity → Detail DTO)
    default EventDetailResponse toEventDetailResponse(Event event) {
        if (event instanceof ScheduledEvent) {
            return toScheduledEventResponse((ScheduledEvent) event);
        } else if (event instanceof UnscheduledEvent) {
            return toUnscheduledEventResponse((UnscheduledEvent) event);
        }
        throw new IllegalArgumentException("Unsupported event type");
    }

    // Specific types (Entity → Response DTO)
    @Mapping(target = "regulationNumber", source = "regulationNumber")
    ScheduledEventResponse toScheduledEventResponse(ScheduledEvent event);

    @Mapping(target = "cause", source = "cause")
    UnscheduledEventResponse toUnscheduledEventResponse(UnscheduledEvent event);

    // Requests → Entities
    Event fromEventRequest(EventRequest request);
    ScheduledEvent fromScheduledEventRequest(ScheduledEventRequest request);
    UnscheduledEvent fromUnscheduledEventRequest(UnscheduledEventRequest request);

    // Roadblocks
    RoadblockResponse toRoadblockResponse(Roadblock roadblock);
    List<RoadblockResponse> toRoadblockResponseList(List<Roadblock> roadblocks);

    // Entities → Requests (for updates)
    EventRequest toEventRequest(Event event);
    ScheduledEventRequest toScheduledEventRequest(ScheduledEvent scheduledEvent);
    UnscheduledEventRequest toUnscheduledEventRequest(UnscheduledEvent unscheduledEvent);

}
