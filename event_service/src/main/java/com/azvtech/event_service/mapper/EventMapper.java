package com.azvtech.event_service.mapper;

import com.azvtech.event_service.dto.EventDto;
import com.azvtech.event_service.dto.ScheduledEventDto;
import com.azvtech.event_service.dto.UnscheduledEventDto;
import com.azvtech.event_service.model.Event;
import com.azvtech.event_service.model.Roadblock;
import com.azvtech.event_service.model.ScheduledEvent;
import com.azvtech.event_service.model.UnscheduledEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDto eventToDto(Event event);

    Roadblock roadblockToDto (Roadblock roadblock);

    ScheduledEventDto scheduledEventToDto(ScheduledEvent scheduledEvent);

    UnscheduledEventDto unscheduledEventToDto(UnscheduledEvent unscheduledEvent);

    @Mapping(target = "id", ignore = true) // Assuming we create a new entity with some generated ID.
    Event eventToEntity(EventDto eventDTO);

    @Mapping(target = "id", ignore = true)
    ScheduledEvent scheduledEventToEntity(ScheduledEventDto scheduledEventDTO);

    @Mapping(target = "id", ignore = true)
    UnscheduledEvent unscheduledEventToEntity(UnscheduledEventDto unscheduledEventDTO);
}
