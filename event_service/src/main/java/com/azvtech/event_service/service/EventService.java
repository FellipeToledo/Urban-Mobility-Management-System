package com.azvtech.event_service.service;

import com.azvtech.event_service.enums.Severity;
import com.azvtech.event_service.enums.Status;
import com.azvtech.event_service.model.Event;
import com.azvtech.event_service.model.ScheduledEvent;
import com.azvtech.event_service.model.UnscheduledEvent;
import com.azvtech.event_service.repository.EventRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> findEventById(Long id) {
        return eventRepository.findById(id);
    }

    public ScheduledEvent createScheduledEvent(ScheduledEvent scheduledEvent) {
        setDefaultValues(scheduledEvent);
        return eventRepository.save(scheduledEvent);
    }
    public UnscheduledEvent createUnscheduledEvent(@Valid UnscheduledEvent unscheduledEvent) {
        setDefaultValues(unscheduledEvent);
        return eventRepository.save(unscheduledEvent);
    }

    public Optional<Event> updateEventFields(Long id, Event updateEvent) {
        Optional<Event> existingEvent = eventRepository.findById(id);

        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();

            // Atualiza campos compartilhados entre todos os eventos
            updateCommonFields(event, updateEvent);

            // Atualiza campos específicos com base no tipo de evento
            if (event instanceof ScheduledEvent) {
                updateScheduledEventFields((ScheduledEvent) event, (ScheduledEvent) updateEvent);
                return Optional.of(eventRepository.save((ScheduledEvent) event));
            } else if (event instanceof UnscheduledEvent) {
                updateUnscheduledEventFields((UnscheduledEvent) event, (UnscheduledEvent) updateEvent);
                return Optional.of(eventRepository.save((UnscheduledEvent) event));
            }
        }

        return Optional.empty(); // Evento não encontrado
    }

    public ScheduledEvent updateScheduledEvent(Long id, ScheduledEvent updatedScheduledEvent) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            ScheduledEvent updatedEvent = (ScheduledEvent) existingEvent.get();
            return eventRepository.save(updatedEvent);
        } else {
            throw new RuntimeException("Evento programado não encontrado");
        }
    }

    public UnscheduledEvent updateUnscheduledEvent(Long id, UnscheduledEvent unscheduledEvent) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            UnscheduledEvent updatedUnscheduledEvent = (UnscheduledEvent) existingEvent.get();
            return eventRepository.save(updatedUnscheduledEvent);
        } else {
            throw new RuntimeException("Evento programado não encontrado");
        }
    }

    public boolean deleteEvent(Long id) {
        return eventRepository.findById(id)
                .map(event -> {
                    eventRepository.deleteById(id);
                    return true;
                }).orElse(false);  // Return false if the event doesn't exist
    }

    private void setDefaultValues(Event event) {
        if (event.getRegistrationDateTime() == null) {
            event.setRegistrationDateTime(LocalDateTime.now());
        }
        if (event.getSeverity() == null) {
            event.setSeverity(Severity.LOW);
        }
        if (event.getStatus() == null) {
            event.setStatus(Status.PENDING);
        }
    }

    private void updateCommonFields(Event existingEvent, Event updatedEvent) {
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setSeverity(updatedEvent.getSeverity());
        existingEvent.setStatus(updatedEvent.getStatus());
        existingEvent.setRoadblocks(updatedEvent.getRoadblocks());
    }

    private void updateScheduledEventFields(ScheduledEvent existingEvent, ScheduledEvent updatedEvent) {
        existingEvent.setNeighborhood(updatedEvent.getNeighborhood());
        existingEvent.setRegulationDate(updatedEvent.getRegulationDate());
        existingEvent.setRegulationId(updatedEvent.getRegulationId());
        existingEvent.setRoadblockDate(updatedEvent.getRoadblockDate());
    }

    private void updateUnscheduledEventFields(UnscheduledEvent existingEvent, UnscheduledEvent updatedEvent) {
        existingEvent.setCategory(updatedEvent.getCategory());
    }
}
