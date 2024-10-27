package com.azvtech.event_service.dao;

import com.azvtech.event_service.model.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Busca de eventos por status (aberto, fechado, etc.)
    public List<Event> findEventsByStatus(String status) {
        return entityManager.createQuery("SELECT e FROM Event e WHERE e.status = :status", Event.class)
                .setParameter("status", status)
                .getResultList();
    }

    // Busca de eventos por severidade
    public List<Event> findEventsBySeverity(String severity) {
        return entityManager.createQuery("SELECT e FROM Event e WHERE e.severity = :severity", Event.class)
                .setParameter("severity", severity)
                .getResultList();
    }
}
