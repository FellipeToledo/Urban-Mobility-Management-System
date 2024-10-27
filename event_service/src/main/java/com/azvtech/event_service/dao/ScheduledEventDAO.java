package com.azvtech.event_service.dao;

import com.azvtech.event_service.model.ScheduledEvent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ScheduledEventDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Busca de eventos programados em uma data específica
    public List<ScheduledEvent> findScheduledEventsByDate(LocalDate date) {
        return entityManager.createQuery("SELECT se FROM ScheduledEvent se WHERE se.regulationDate = :date", ScheduledEvent.class)
                .setParameter("date", date)
                .getResultList();
    }

    // Busca por eventos programados em determinado bairro
    public List<ScheduledEvent> findScheduledEventsByNeighborhood(String neighborhood) {
        return entityManager.createQuery("SELECT se FROM ScheduledEvent se WHERE se.neighborhood = :neighborhood", ScheduledEvent.class)
                .setParameter("neighborhood", neighborhood)
                .getResultList();
    }
}
