package com.azvtech.event_service.dao;

import com.azvtech.event_service.enums.Severity;
import com.azvtech.event_service.enums.Status;
import com.azvtech.event_service.model.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Event> findEventsByStatus(Status status) {
        return entityManager.createQuery("SELECT e FROM Event e WHERE e.status = :status", Event.class)
                .setParameter("status", status)
                .getResultList();
    }

    public List<Event> findEventsBySeverity(Severity severity) {
        return entityManager.createQuery("SELECT e FROM Event e WHERE e.severity = :severity", Event.class)
                .setParameter("severity", severity)
                .getResultList();
    }
}
