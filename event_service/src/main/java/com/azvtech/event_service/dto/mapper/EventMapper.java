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

    // Listagem
    EventListResponse toListResponse(Event event);

    // Detalhes

    default EventDetailResponse toDetailResponse(Event event) {
        if (event instanceof ScheduledEvent) {
            return toScheduledResponse((ScheduledEvent) event);
        } else if (event instanceof UnscheduledEvent) {
            return toUnscheduledResponse((UnscheduledEvent) event);
        }
        throw new IllegalArgumentException("Tipo de evento não suportado");
    }

    @Mapping(target = "regulationNumber", source = "regulationNumber")
    @Mapping(target = "regulationPublicationDate", source = "regulationPublicationDate")
    ScheduledEventResponse toScheduledResponse(ScheduledEvent event);

    @Mapping(target = "cause", source = "cause")
    UnscheduledEventResponse toUnscheduledResponse(UnscheduledEvent event);

    // Requests → Entidades
    Event fromRequest(EventRequest request);
    ScheduledEvent fromScheduledRequest(ScheduledEventRequest request);

    // Roadblocks
    RoadblockResponse toRoadblockResponse(Roadblock roadblock);
    List<RoadblockResponse> mapRoadblocks(List<Roadblock> roadblocks);

    //TO  ENTITY
    EventRequest eventToDto(Event event);
    ScheduledEvent scheduledEventToEntity(ScheduledEventRequest scheduledEventDTO);
    UnscheduledEvent unscheduledEventToEntity(UnscheduledEventRequest unscheduledEventDTO);

    //TO REQUEST DTO
    ScheduledEventRequest scheduledEventToDto(ScheduledEvent scheduledEvent);
    UnscheduledEventRequest unscheduledEventToDto(UnscheduledEvent unscheduledEvent);

   /* EventRequest eventToDto(Event event);
    EventListResponse modelToDto(Event event);

    default RoadblockResponse map(Roadblock roadblock) {
        if (roadblock == null) {
            return null;
        }
        RoadblockResponse dto = new RoadblockResponse();
        dto.setRoad(roadblock.getRoad());
        dto.setStartRoad(roadblock.getStartRoad());
        dto.setEndRoad(roadblock.getEndRoad());
        dto.setStartDateTime(roadblock.getStartDateTime());
        dto.setEndDateTime(roadblock.getEndDateTime());
        return dto;
    }

    Roadblock roadblockToDto (Roadblock roadblock);

    ScheduledEventRequest scheduledEventToDto(ScheduledEvent scheduledEvent);

    UnscheduledEventRequest unscheduledEventToDto(UnscheduledEvent unscheduledEvent);

    @Mapping(target = "id", ignore = true) // Assuming we create a new entity with some generated ID.
    Event eventToEntity(EventRequest eventRequest);

    @Mapping(target = "id", ignore = true)
    ScheduledEvent scheduledEventToEntity(ScheduledEventRequest scheduledEventDTO);

    @Mapping(target = "id", ignore = true)
    UnscheduledEvent unscheduledEventToEntity(UnscheduledEventRequest unscheduledEventDTO);

    */

}
