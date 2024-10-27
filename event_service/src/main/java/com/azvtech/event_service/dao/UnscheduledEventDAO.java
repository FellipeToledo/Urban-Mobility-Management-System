package com.azvtech.event_service.dao;

import com.azvtech.event_service.model.UnscheduledEvent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UnscheduledEventDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Busca de eventos não programados por categoria (ex: acidente, protesto)
    public List<UnscheduledEvent> findUnscheduledEventsByCategory(String category) {
        return entityManager.createQuery("SELECT ue FROM UnscheduledEvent ue WHERE ue.category = :category", UnscheduledEvent.class)
                .setParameter("category", category)
                .getResultList();
    }

    // Busca de eventos não programados por severidade
    public List<UnscheduledEvent> findUnscheduledEventsBySeverity(String severity) {
        return entityManager.createQuery("SELECT ue FROM UnscheduledEvent ue WHERE ue.severity = :severity", UnscheduledEvent.class)
                .setParameter("severity", severity)
                .getResultList();
    }
}
