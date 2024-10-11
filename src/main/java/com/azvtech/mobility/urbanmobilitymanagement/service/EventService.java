package com.azvtech.mobility.urbanmobilitymanagement.service;

import com.azvtech.mobility.urbanmobilitymanagement.model.Event;
import com.azvtech.mobility.urbanmobilitymanagement.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
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
        event.setRegistrationDateTime(LocalDateTime.now());
        return eventRepository.save(event);
    }

    public Optional<Event> updateEvent(Long id, Event updatedEvent) {
        return eventRepository.findById(id)
                .map(event ->  {
                    event.setDescription(updatedEvent.getDescription());
                    event.setLocation(updatedEvent.getLocation());
                    event.setRegistrationDateTime(updatedEvent.getRegistrationDateTime());
                    event.setStartDateTime(updatedEvent.getStartDateTime());
                    event.setEndDateTime(updatedEvent.getEndDateTime());
                    event.setSeverity(updatedEvent.getSeverity());
                    event.setStatus(updatedEvent.getStatus());
                    event.setImpactTransit(updatedEvent.getImpactTransit());
                    event.setRoadBlock(updatedEvent.isRoadBlock());
                    return eventRepository.save(event);
                });
    }

    public boolean deleteEvent(Long id) {
        return eventRepository.findById(id)
                .map(event -> {
                    eventRepository.deleteById(id);
                    return true;
                }).orElse(false);  // Return false if the event doesn't exist
    }
}
