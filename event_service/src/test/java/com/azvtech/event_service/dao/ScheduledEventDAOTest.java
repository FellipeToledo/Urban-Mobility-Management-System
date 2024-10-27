package com.azvtech.event_service.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.azvtech.event_service.model.ScheduledEvent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

public class ScheduledEventDAOTest {
    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<ScheduledEvent> query;

    @InjectMocks
    private ScheduledEventDAO scheduledEventDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindScheduledEventsByDate() {
        LocalDate date = LocalDate.now();
        when(entityManager.createQuery("SELECT se FROM ScheduledEvent se WHERE se.regulationDate = :date", ScheduledEvent.class))
                .thenReturn(query);
        when(query.setParameter("date", date)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new ScheduledEvent()));

        List<ScheduledEvent> events = scheduledEventDAO.findScheduledEventsByDate(date);

        assertEquals(1, events.size());
        verify(entityManager, times(1)).createQuery("SELECT se FROM ScheduledEvent se WHERE se.regulationDate = :date", ScheduledEvent.class);
        verify(query, times(1)).setParameter("date", date);
        verify(query, times(1)).getResultList();
    }

    @Test
    void testFindScheduledEventsByNeighborhood() {
        String neighborhood = "Centro";

        // Configuração do mock para criar a consulta e definir parâmetros
        when(entityManager.createQuery("SELECT se FROM ScheduledEvent se WHERE se.neighborhood = :neighborhood", ScheduledEvent.class))
                .thenReturn(query);
        when(query.setParameter("neighborhood", neighborhood)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new ScheduledEvent()));

        // Execução do método
        List<ScheduledEvent> events = scheduledEventDAO.findScheduledEventsByNeighborhood(neighborhood);

        // Verificação dos resultados
        assertEquals(1, events.size());
        verify(entityManager, times(1)).createQuery("SELECT se FROM ScheduledEvent se WHERE se.neighborhood = :neighborhood", ScheduledEvent.class);
        verify(query, times(1)).setParameter("neighborhood", neighborhood);
        verify(query, times(1)).getResultList();
    }
}
