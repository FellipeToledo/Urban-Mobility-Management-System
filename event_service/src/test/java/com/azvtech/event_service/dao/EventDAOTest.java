package com.azvtech.event_service.dao;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.azvtech.event_service.enums.Severity;
import com.azvtech.event_service.enums.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.azvtech.event_service.model.Event;

import java.util.List;

public class EventDAOTest {
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private EventDAO eventDAO;

    @Mock
    private TypedQuery<Event> query;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindEventsByStatus() {
        String status = "OPEN";
        when(entityManager.createQuery(anyString(), eq(Event.class))).thenReturn(query);
        when(query.setParameter(anyString(), eq(status))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Event()));

        List<Event> result = eventDAO.findEventsByStatus(Status.valueOf(status));

        assertNotNull(result);
        assertFalse(result.isEmpty(), "Deve retornar ao menos um evento para o status informado");
        verify(entityManager).createQuery("SELECT e FROM Event e WHERE e.status = :status", Event.class);
        verify(query).setParameter("status", status);
    }

    @Test
    void testFindEventsBySeverity() {
        String severity = "HIGH";
        when(entityManager.createQuery(anyString(), eq(Event.class))).thenReturn(query);
        when(query.setParameter(anyString(), eq(severity))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Event()));

        List<Event> result = eventDAO.findEventsBySeverity(Severity.valueOf(severity));

        assertNotNull(result);
        assertFalse(result.isEmpty(), "Deve retornar ao menos um evento para a severidade informada");
        verify(entityManager).createQuery("SELECT e FROM Event e WHERE e.severity = :severity", Event.class);
        verify(query).setParameter("severity", severity);
    }
}
