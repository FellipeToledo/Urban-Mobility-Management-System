package com.azvtech.event_service.mapper;

import com.azvtech.event_service.dto.EventDto;
import com.azvtech.event_service.dto.ScheduledEventDto;
import com.azvtech.event_service.dto.UnscheduledEventDto;
import com.azvtech.event_service.model.Event;
import com.azvtech.event_service.model.ScheduledEvent;
import com.azvtech.event_service.model.UnscheduledEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDto toDto(Event event);

    ScheduledEventDto toDto(ScheduledEvent scheduledEvent);

    UnscheduledEventDto toDto(UnscheduledEvent unscheduledEvent);

    @Mapping(target = "id", ignore = true) // Assuming we create a new entity with some generated ID.
    Event toEntity(EventDto eventDTO);

    @Mapping(target = "id", ignore = true)
    ScheduledEvent toEntity(ScheduledEventDto scheduledEventDTO);

    @Mapping(target = "id", ignore = true)
    UnscheduledEvent toEntity(UnscheduledEventDto unscheduledEventDTO);

}
