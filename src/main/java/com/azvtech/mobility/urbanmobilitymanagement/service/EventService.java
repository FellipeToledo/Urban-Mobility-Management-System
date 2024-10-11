package com.azvtech.mobility.urbanmobilitymanagement.service;

import com.azvtech.mobility.urbanmobilitymanagement.enums.Severity;
import com.azvtech.mobility.urbanmobilitymanagement.enums.Status;
import com.azvtech.mobility.urbanmobilitymanagement.model.Event;
import com.azvtech.mobility.urbanmobilitymanagement.repository.EventRepository;
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

    public Event createEvent(Event event) {
        setDefaultValues(event);
        return eventRepository.save(event);
    }

    public Optional<Event> updateEvent(Long id, Event updatedEvent) {
        return eventRepository.findById(id)
                .map(existingEvent -> {
                    updateEventFields(existingEvent, updatedEvent);
                    return eventRepository.save(existingEvent);
                });
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

    private void updateEventFields(Event existingEvent, Event updatedEvent) {
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setStartDateTime(updatedEvent.getStartDateTime());
        existingEvent.setEndDateTime(updatedEvent.getEndDateTime());
        existingEvent.setLocation(updatedEvent.getLocation());
        existingEvent.setSeverity(updatedEvent.getSeverity());
        existingEvent.setStatus(updatedEvent.getStatus());
    }
}
