package com.azvtech.event_service.dao;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.azvtech.event_service.model.UnscheduledEvent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
public class UnscheduledEventDAOTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<UnscheduledEvent> query;

    @InjectMocks
    private UnscheduledEventDAO unscheduledEventDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUnscheduledEventsByCategory() {
        String category = "Accident";
        when(entityManager.createQuery("SELECT ue FROM UnscheduledEvent ue WHERE ue.category = :category", UnscheduledEvent.class))
                .thenReturn(query);
        when(query.setParameter("category", category)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new UnscheduledEvent()));

        List<UnscheduledEvent> events = unscheduledEventDAO.findUnscheduledEventsByCategory(category);

        assertEquals(1, events.size());
        verify(entityManager, times(1)).createQuery("SELECT ue FROM UnscheduledEvent ue WHERE ue.category = :category", UnscheduledEvent.class);
        verify(query, times(1)).setParameter("category", category);
        verify(query, times(1)).getResultList();
    }

    @Test
    void testFindUnscheduledEventsBySeverity() {
        String severity = "High";

        // Configuração do mock para criar a consulta e definir parâmetros
        when(entityManager.createQuery("SELECT ue FROM UnscheduledEvent ue WHERE ue.severity = :severity", UnscheduledEvent.class))
                .thenReturn(query);
        when(query.setParameter("severity", severity)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new UnscheduledEvent()));

        // Execução do método
        List<UnscheduledEvent> events = unscheduledEventDAO.findUnscheduledEventsBySeverity(severity);

        // Verificação dos resultados
        assertEquals(1, events.size());
        verify(entityManager, times(1)).createQuery("SELECT ue FROM UnscheduledEvent ue WHERE ue.severity = :severity", UnscheduledEvent.class);
        verify(query, times(1)).setParameter("severity", severity);
        verify(query, times(1)).getResultList();
    }
}
