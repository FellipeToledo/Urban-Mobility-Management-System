package com.azvtech.event_service.service;

import com.azvtech.event_service.dao.EventDAO;
import com.azvtech.event_service.dao.ScheduledEventDAO;
import com.azvtech.event_service.dao.UnscheduledEventDAO;
import com.azvtech.event_service.dto.CreateEventDto;
import com.azvtech.event_service.dto.ScheduledCreateEventDto;
import com.azvtech.event_service.dto.UnscheduledCreateEventDto;
import com.azvtech.event_service.enums.Severity;
import com.azvtech.event_service.enums.Status;
import com.azvtech.event_service.mapper.EventMapper;
import com.azvtech.event_service.model.Event;
import com.azvtech.event_service.model.ScheduledEvent;
import com.azvtech.event_service.model.UnscheduledEvent;
import com.azvtech.event_service.repository.EventRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

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

    public List<CreateEventDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(eventMapper::eventToDto)
                .collect(Collectors.toList());
    }

    public List<CreateEventDto> getEventsByRoadblock(String road) {
        List<Event> events = eventRepository.findAll();
        List<Event> filteredEvents = events.stream()
                .filter(event -> event.getRoadblocks().stream()
                        .anyMatch(roadblock -> roadblock.getRoad().equalsIgnoreCase(road)))
                .toList();
        return filteredEvents.stream()
                .map(eventMapper::eventToDto)
                .collect(Collectors.toList());
    }

    public Optional<CreateEventDto> getEventById(Long id) {
        return eventRepository.findById(id).map(eventMapper::eventToDto);
    }

    public boolean createScheduledEvent(@Valid ScheduledCreateEventDto scheduledEventDTO) {
        try {
            ScheduledEvent scheduledEvent = eventMapper.scheduledEventToEntity(scheduledEventDTO);
            setDefaultValues(scheduledEvent);
            eventRepository.save(scheduledEvent);
            log.info("Evento programado criado com sucesso: {}", scheduledEvent);
            return true;
        } catch (Exception e) {
            log.error("Erro ao criar evento programado: {}", e.getMessage(), e);
            return false;
        }
    }

    public UnscheduledCreateEventDto createUnscheduledEvent(@Valid UnscheduledCreateEventDto unscheduledEventDTO) {
        UnscheduledEvent unscheduledEvent = eventMapper.unscheduledEventToEntity(unscheduledEventDTO);
        setDefaultValues(unscheduledEvent);
        UnscheduledEvent savedEvent = eventRepository.save(unscheduledEvent);
        return eventMapper.unscheduledEventToDto(savedEvent);
    }

    public Optional<CreateEventDto> updateEventFields(Long id, CreateEventDto createEventDTO) {
        return eventRepository.findById(id).map(existingEvent -> {
            updateCommonFields(existingEvent, createEventDTO);
            if (existingEvent instanceof ScheduledEvent && createEventDTO instanceof ScheduledCreateEventDto) {
                updateScheduledEventFields((ScheduledEvent) existingEvent, (ScheduledCreateEventDto) createEventDTO);
            } else if (existingEvent instanceof UnscheduledEvent && createEventDTO instanceof UnscheduledCreateEventDto) {
                updateUnscheduledEventFields((UnscheduledEvent) existingEvent, (UnscheduledCreateEventDto) createEventDTO);
            }
            return eventMapper.eventToDto(eventRepository.save(existingEvent));
        });
    }

    public boolean deleteEvent(Long id) {
        return eventRepository.findById(id).map(event -> {
            eventRepository.deleteById(id);
            return true;
        }).orElse(false);
    }

    private void updateCommonFields(Event existingEvent, CreateEventDto createEventDTO) {
        existingEvent.setDescription(createEventDTO.getDescription());
        existingEvent.setSeverity(createEventDTO.getSeverity());
        existingEvent.setStatus(createEventDTO.getStatus());
    }

    private void updateScheduledEventFields(ScheduledEvent existingEvent, ScheduledCreateEventDto scheduledEventDTO) {
        existingEvent.setNeighborhood(scheduledEventDTO.getNeighborhood());
        existingEvent.setRegulationDate(scheduledEventDTO.getRegulationDate());
        existingEvent.setRegulationNumber(scheduledEventDTO.getRegulationNumber());
    }

    private void updateUnscheduledEventFields(UnscheduledEvent existingEvent, UnscheduledCreateEventDto unscheduledEventDTO) {
        existingEvent.setCategory(unscheduledEventDTO.getCategory());
    }

    private void setDefaultValues(Event event) {
        if (event.getRegistrationDateTime() == null) {
            event.setRegistrationDateTime(LocalDateTime.now());
        }
    }

    public List<ScheduledCreateEventDto> getScheduledEventsByDate(LocalDate date) {
        return scheduledEventDAO.findScheduledEventsByDate(date).stream()
                .map(eventMapper::scheduledEventToDto)
                .collect(Collectors.toList());
    }

    public List<ScheduledCreateEventDto> getScheduledEventsByNeighborhood(String neighborhood) {
        return scheduledEventDAO.findScheduledEventsByNeighborhood(neighborhood).stream()
                .map(eventMapper::scheduledEventToDto)
                .collect(Collectors.toList());
    }

    public List<CreateEventDto> getEventsByStatus(String status) {
        Status eventStatus = Status.valueOf(status.toUpperCase());
        return eventDAO.findEventsByStatus(eventStatus).stream()
                .map(eventMapper::eventToDto)
                .collect(Collectors.toList());
    }

    public List<CreateEventDto> getEventsBySeverity(String severity) {
        Severity eventSeverity = Severity.valueOf(severity.toUpperCase());
        return eventDAO.findEventsBySeverity(eventSeverity).stream()
                .map(eventMapper::eventToDto)
                .collect(Collectors.toList());
    }

    public List<UnscheduledCreateEventDto> getUnscheduledEventsByCategory(String category) {
        return unscheduledEventDAO.findUnscheduledEventsByCategory(category).stream()
                .map(eventMapper::unscheduledEventToDto)
                .collect(Collectors.toList());
    }
}
