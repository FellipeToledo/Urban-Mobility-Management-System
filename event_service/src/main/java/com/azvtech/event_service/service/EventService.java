package com.azvtech.event_service.service;

import com.azvtech.event_service.dao.EventDAO;
import com.azvtech.event_service.dao.ScheduledEventDAO;
import com.azvtech.event_service.dao.UnscheduledEventDAO;
import com.azvtech.event_service.dto.EventDto;
import com.azvtech.event_service.dto.ScheduledEventDto;
import com.azvtech.event_service.dto.UnscheduledEventDto;
import com.azvtech.event_service.enums.Severity;
import com.azvtech.event_service.enums.Status;
import com.azvtech.event_service.mapper.EventMapper;
import com.azvtech.event_service.model.Event;
import com.azvtech.event_service.model.ScheduledEvent;
import com.azvtech.event_service.model.UnscheduledEvent;
import com.azvtech.event_service.repository.EventRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for managing events, both scheduled and unscheduled.
 */
@Service
@Transactional
public class EventService {

    private static final Severity DEFAULT_SEVERITY = Severity.LOW;
    private static final Status DEFAULT_STATUS = Status.PENDING;

    private final EventRepository eventRepository;
    private final EventDAO eventDAO;
    private final ScheduledEventDAO scheduledEventDAO;
    private final UnscheduledEventDAO unscheduledEventDAO;
    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventRepository eventRepository, EventDAO eventDAO, ScheduledEventDAO scheduledEventDAO, UnscheduledEventDAO unscheduledEventDAO, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventDAO = eventDAO;
        this.scheduledEventDAO = scheduledEventDAO;
        this.unscheduledEventDAO = unscheduledEventDAO;
        this.eventMapper = eventMapper;
    }

    public List<EventDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getEventsByRoadblock(String road) {
        List<Event> events = eventRepository.findAll();
        List<Event> filteredEvents = events.stream()
                .filter(event -> event.getRoadblocks().stream()
                        .anyMatch(roadblock -> roadblock.getRoad().equalsIgnoreCase(road)))
                .toList();
        return filteredEvents.stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<EventDto> getEventById(Long id) {
        return eventRepository.findById(id).map(eventMapper::toDto);
    }

    public ScheduledEventDto createScheduledEvent(@Valid ScheduledEventDto scheduledEventDTO) {
        ScheduledEvent scheduledEvent = eventMapper.toEntity(scheduledEventDTO);
        setDefaultValues(scheduledEvent);
        ScheduledEvent savedEvent = eventRepository.save(scheduledEvent);
        return eventMapper.toDto(savedEvent);
    }

    public UnscheduledEventDto createUnscheduledEvent(@Valid UnscheduledEventDto unscheduledEventDTO) {
        UnscheduledEvent unscheduledEvent = eventMapper.toEntity(unscheduledEventDTO);
        setDefaultValues(unscheduledEvent);
        UnscheduledEvent savedEvent = eventRepository.save(unscheduledEvent);
        return eventMapper.toDto(savedEvent);
    }

    public Optional<EventDto> updateEventFields(Long id, EventDto eventDTO) {
        return eventRepository.findById(id).map(existingEvent -> {
            updateCommonFields(existingEvent, eventDTO);
            if (existingEvent instanceof ScheduledEvent && eventDTO instanceof ScheduledEventDto) {
                updateScheduledEventFields((ScheduledEvent) existingEvent, (ScheduledEventDto) eventDTO);
            } else if (existingEvent instanceof UnscheduledEvent && eventDTO instanceof UnscheduledEventDto) {
                updateUnscheduledEventFields((UnscheduledEvent) existingEvent, (UnscheduledEventDto) eventDTO);
            }
            return eventMapper.toDto(eventRepository.save(existingEvent));
        });
    }

    public boolean deleteEvent(Long id) {
        return eventRepository.findById(id).map(event -> {
            eventRepository.deleteById(id);
            return true;
        }).orElse(false);
    }

    private void updateCommonFields(Event existingEvent, EventDto eventDTO) {
        existingEvent.setDescription(eventDTO.getDescription());
        existingEvent.setSeverity(eventDTO.getSeverity());
        existingEvent.setStatus(eventDTO.getStatus());
        existingEvent.setRoadblocks(eventDTO.getRoadblocks());
    }

    private void updateScheduledEventFields(ScheduledEvent existingEvent, ScheduledEventDto scheduledEventDTO) {
        existingEvent.setNeighborhood(scheduledEventDTO.getNeighborhood());
        existingEvent.setRegulationDate(scheduledEventDTO.getRegulationDate());
        existingEvent.setRegulationId(scheduledEventDTO.getRegulationId());
        existingEvent.setRoadblockDate(scheduledEventDTO.getRoadblockDate());
    }

    private void updateUnscheduledEventFields(UnscheduledEvent existingEvent, UnscheduledEventDto unscheduledEventDTO) {
        existingEvent.setCategory(unscheduledEventDTO.getCategory());
    }

    private void setDefaultValues(Event event) {
        if (event.getRegistrationDateTime() == null) {
            event.setRegistrationDateTime(LocalDateTime.now());
        }
        if (event.getSeverity() == null) {
            event.setSeverity(DEFAULT_SEVERITY);
        }
        if (event.getStatus() == null) {
            event.setStatus(DEFAULT_STATUS);
        }
    }

    public List<ScheduledEventDto> getScheduledEventsByDate(LocalDate date) {
        return scheduledEventDAO.findScheduledEventsByDate(date).stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ScheduledEventDto> getScheduledEventsByNeighborhood(String neighborhood) {
        return scheduledEventDAO.findScheduledEventsByNeighborhood(neighborhood).stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getEventsByStatus(String status) {
        Status eventStatus = Status.valueOf(status.toUpperCase());
        return eventDAO.findEventsByStatus(eventStatus).stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getEventsBySeverity(String severity) {
        Severity eventSeverity = Severity.valueOf(severity.toUpperCase());
        return eventDAO.findEventsBySeverity(eventSeverity).stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UnscheduledEventDto> getUnscheduledEventsByCategory(String category) {
        return unscheduledEventDAO.findUnscheduledEventsByCategory(category).stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }
}
