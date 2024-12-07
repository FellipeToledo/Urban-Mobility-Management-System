package com.azvtech.event_service.dto.mapper;

import com.azvtech.event_service.dto.CreateEventDto;
import com.azvtech.event_service.dto.ScheduledCreateEventDto;
import com.azvtech.event_service.dto.UnscheduledCreateEventDto;
import com.azvtech.event_service.model.Event;
import com.azvtech.event_service.model.Roadblock;
import com.azvtech.event_service.model.ScheduledEvent;
import com.azvtech.event_service.model.UnscheduledEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    CreateEventDto eventToDto(Event event);

    Roadblock roadblockToDto (Roadblock roadblock);

    ScheduledCreateEventDto scheduledEventToDto(ScheduledEvent scheduledEvent);

    UnscheduledCreateEventDto unscheduledEventToDto(UnscheduledEvent unscheduledEvent);

    @Mapping(target = "id", ignore = true) // Assuming we create a new entity with some generated ID.
    Event eventToEntity(CreateEventDto createEventDTO);

    @Mapping(target = "id", ignore = true)
    ScheduledEvent scheduledEventToEntity(ScheduledCreateEventDto scheduledEventDTO);

    @Mapping(target = "id", ignore = true)
    UnscheduledEvent unscheduledEventToEntity(UnscheduledCreateEventDto unscheduledEventDTO);
}
