package com.azvtech.mobility.urbanmobilitymanagement.service;

import com.azvtech.mobility.urbanmobilitymanagement.model.Event;
import com.azvtech.mobility.urbanmobilitymanagement.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        return eventRepository.findById(id)
                .map(event -> {
                    event.setDescription(updatedEvent.getDescription());
                    event.setDateTime(updatedEvent.getDateTime());
                    event.setLocation(updatedEvent.getLocation());
                    event.setSeverity(updatedEvent.getSeverity());
                    event.setStatus(updatedEvent.getStatus());
                    event.setImpactTransit(updatedEvent.getImpactTransit());
                    event.setRoadblock(updatedEvent.isRoadblock());
                    return eventRepository.save(event);
                }).orElseThrow(() -> new RuntimeException("Evento não encontrado"));
    }
}
