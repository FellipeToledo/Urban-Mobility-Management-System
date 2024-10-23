package com.azvtech.event_service.controller;

import com.azvtech.event_service.model.Event;
import com.azvtech.event_service.model.ScheduledEvent;
import com.azvtech.event_service.model.UnscheduledEvent;
import com.azvtech.event_service.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Criar um novo evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criar o evento")
    })
    @PostMapping("/scheduled")
    public ResponseEntity<ScheduledEvent> createScheduledEvent(
            @Valid
            @RequestBody ScheduledEvent scheduledEvent) {
        ScheduledEvent newScheduledEvent = eventService.createScheduledEvent(scheduledEvent);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/resource/" + newScheduledEvent.getId())
                .body(newScheduledEvent);
    }

    @PostMapping("/unscheduled")
    public ResponseEntity<UnscheduledEvent> createUnscheduledEvent(
            @Valid
            @RequestBody UnscheduledEvent unscheduledEvent) {
        UnscheduledEvent newUnscheduledEvent = eventService.createUnscheduledEvent(unscheduledEvent);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/resource/" + newUnscheduledEvent.getId())
                .body(newUnscheduledEvent);
    }

    @Operation(summary = "Buscar todos os eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.findAllEvents();
        return events.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(events);
    }

    @Operation(summary = "Buscar um evento pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(
            @Parameter(description = "ID do evento a ser buscado")
            @PathVariable Long id) {
        return eventService.findEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar um evento programado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
        @PutMapping("/scheduled/{id}")
        public ResponseEntity<?> updateScheduledEvent(
                @Parameter(description = "ID do evento a ser atualizado")
                @PathVariable Long id,
                @RequestBody ScheduledEvent scheduledEvent) {
            try {
                Optional<Event> updatedEvent = eventService.updateEventFields(id, scheduledEvent);

                // Verifica se o evento foi atualizado com sucesso
                if (updatedEvent.isPresent()) {
                    return ResponseEntity.ok(updatedEvent.get());
                } else {
                    return ResponseEntity.notFound().build(); // Evento não encontrado
                }

            } catch (ClassCastException e) {
                return ResponseEntity.badRequest().body("O evento com o ID fornecido não é do tipo EventoProgramado");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Erro ao atualizar o evento: " + e.getMessage());
            }
        }

    @Operation(summary = "Atualizar um evento não programado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    @PutMapping("/unscheduled/{id}")
    public ResponseEntity<?> updateUnscheduledEvent(
            @Parameter(description = "ID do evento a ser atualizado")
            @PathVariable Long id,
            @RequestBody UnscheduledEvent unscheduledEvent) {
        try {
            Optional<Event> updatedEvent = eventService.updateEventFields(id, unscheduledEvent);

            // Verifica se o evento foi atualizado com sucesso
            if (updatedEvent.isPresent()) {
                return ResponseEntity.ok(updatedEvent.get());
            } else {
                return ResponseEntity.notFound().build(); // Evento não encontrado
            }

        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body("O evento com o ID fornecido não é do tipo EventoNaoProgramado");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar o evento: " + e.getMessage());
        }
    }

    @Operation(summary = "Deletar um evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(
            @Parameter(description = "ID do evento a ser deletado")
            @PathVariable Long id) {
        return eventService.deleteEvent(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
