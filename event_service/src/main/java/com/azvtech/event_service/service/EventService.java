package com.azvtech.event_service.service;

import com.azvtech.event_service.dto.mapper.EventMapper;
import com.azvtech.event_service.dto.request.EventRequest;
import com.azvtech.event_service.dto.request.ScheduledEventRequest;
import com.azvtech.event_service.dto.request.UnscheduledEventRequest;
import com.azvtech.event_service.dto.response.RoadblockResponse;
import com.azvtech.event_service.dto.response.detail.EventDetailResponse;
import com.azvtech.event_service.dto.response.list.EventListResponse;
import com.azvtech.event_service.exception.EntityNotFoundException;
import com.azvtech.event_service.exception.EventTypeMismatchException;
import com.azvtech.event_service.model.Event;
import com.azvtech.event_service.model.ScheduledEvent;
import com.azvtech.event_service.model.UnscheduledEvent;
import com.azvtech.event_service.repository.EventRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.azvtech.event_service.util.ErrorMessages.EVENT_NOT_FOUND;

/**
 * Service for managing events, both scheduled and unscheduled.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Transactional(readOnly = true)
    public List<EventListResponse> getAllEvents() {
        log.debug("Fetching all events");
        List<Event> events = eventRepository.findAll();
        log.debug("Found {} events", events.size());
        return events.stream()
                .map(eventMapper::toListResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EventRequest> getEventsByRoadblock(String road) {
        log.debug("Fetching events for road: {}", road);
        return filterEventsByRoad(road).stream()
                .map(eventMapper::eventToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RoadblockResponse> getAllRoadblocks() {
        log.debug("Fetching all roadblocks");
        return eventRepository.findAll().stream()
                .flatMap(event -> event.getRoadblocks().stream()
                        .map(eventMapper::toRoadblockResponse))
                .toList();
    }

    @Transactional(readOnly = true)
    public EventDetailResponse getEventDetail(Long id) {
        log.debug("Fetching event detail for id: {}", id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(EVENT_NOT_FOUND, id)));

        return event instanceof ScheduledEvent ?
                eventMapper.toScheduledResponse((ScheduledEvent) event) :
                eventMapper.toDetailResponse(event);
    }

    public boolean createScheduledEvent(@Valid ScheduledEventRequest request) {
        log.debug("Creating scheduled event");
        ScheduledEvent event = eventMapper.scheduledEventToEntity(request);
        setDefaultValues(event);
        eventRepository.save(event);
        log.info("Created scheduled event with id: {}", event.getId());
        return true;
    }

    public UnscheduledEventRequest createUnscheduledEvent(@Valid UnscheduledEventRequest request) {
        log.debug("Creating unscheduled event");
        UnscheduledEvent event = eventMapper.unscheduledEventToEntity(request);
        setDefaultValues(event);
        UnscheduledEvent savedEvent = eventRepository.save(event);
        log.info("Created unscheduled event with id: {}", savedEvent.getId());
        return eventMapper.unscheduledEventToDto(savedEvent);
    }

    public EventRequest updateEvent(Long id, EventRequest request) {
        log.debug("Updating event with id: {}", id);

        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(EVENT_NOT_FOUND, id)));

        validateEventTypeMatch(existingEvent, request);

        updateCommonFields(existingEvent, request);

        if (existingEvent instanceof ScheduledEvent scheduledEvent &&
                request instanceof ScheduledEventRequest scheduledRequest) {
            updateScheduledEventFields(scheduledEvent, scheduledRequest);
        }
        else if (existingEvent instanceof UnscheduledEvent unscheduledEvent &&
                request instanceof UnscheduledEventRequest unscheduledRequest) {
            updateUnscheduledEventFields(unscheduledEvent, unscheduledRequest);
        }

        Event updatedEvent = eventRepository.save(existingEvent);
        log.info("Updated event with id: {}", id);
        return eventMapper.eventToDto(updatedEvent);
    }

    private void validateEventTypeMatch(Event existingEvent, EventRequest request) {
        boolean isScheduledMismatch = existingEvent instanceof ScheduledEvent &&
                !(request instanceof ScheduledEventRequest);

        boolean isUnscheduledMismatch = existingEvent instanceof UnscheduledEvent &&
                !(request instanceof UnscheduledEventRequest);

        if (isScheduledMismatch || isUnscheduledMismatch) {
            String eventType = existingEvent instanceof ScheduledEvent ? "programado" : "não-programado";
            throw new EventTypeMismatchException(
                    String.format("O evento com ID %d é %s e não pode ser atualizado com os atributos fornecidos",
                            existingEvent.getId(), eventType)
            );
        }
    }

    public void deleteEvent(Long id) {
        log.debug("Deleting event with id: {}", id);
        eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(EVENT_NOT_FOUND, id)));
        eventRepository.deleteById(id);
        log.info("Deleted event with id: {}", id);
    }

    // Métodos auxiliares privados
    private List<Event> filterEventsByRoad(String road) {
        return eventRepository.findAll().stream()
                .filter(event -> hasMatchingRoadblock(event, road))
                .toList();
    }

    private boolean hasMatchingRoadblock(Event event, String road) {
        return event.getRoadblocks().stream()
                .anyMatch(rb -> rb.getRoad().equalsIgnoreCase(road));
    }

    private void setDefaultValues(Event event) {
        if (event.getRegistrationDateTime() == null) {
            event.setRegistrationDateTime(LocalDateTime.now());
        }
    }

    public List<RoadblockResponse> getAllRoadblock() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .flatMap(event -> event.getRoadblocks().stream()
                        .map(eventMapper::toRoadblockResponse))
                .collect(Collectors.toList());
    }

    private void updateCommonFields(Event existingEvent, EventRequest eventRequest) {
        existingEvent.setDescription(eventRequest.getDescription());
        existingEvent.setSeverity(eventRequest.getSeverity());
        existingEvent.setStatus(eventRequest.getStatus());
    }

    private void updateScheduledEventFields(ScheduledEvent existingEvent, ScheduledEventRequest scheduledEventDTO) {
        existingEvent.setNeighborhood(scheduledEventDTO.getNeighborhood());
        existingEvent.setRegulationPublicationDate(scheduledEventDTO.getRegulationPublicationDate());
        existingEvent.setRegulationNumber(scheduledEventDTO.getRegulationNumber());
    }

    private void updateUnscheduledEventFields(UnscheduledEvent existingEvent, UnscheduledEventRequest unscheduledEventDTO) {
        existingEvent.setCause(unscheduledEventDTO.getCause());
    }
}
