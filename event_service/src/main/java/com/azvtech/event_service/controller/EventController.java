package com.azvtech.event_service.controller;

import com.azvtech.event_service.dto.EventDto;
import com.azvtech.event_service.dto.ScheduledEventDto;
import com.azvtech.event_service.dto.UnscheduledEventDto;
import com.azvtech.event_service.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Criar um novo evento programado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criar o evento")
    })
    @PostMapping("/scheduled")
    public ResponseEntity<ScheduledEventDto> createScheduledEvent(
            @Valid
            @RequestBody ScheduledEventDto scheduledEvent) {
        ScheduledEventDto newScheduledEvent = eventService.createScheduledEvent(scheduledEvent);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/resource/" + newScheduledEvent)
                .body(newScheduledEvent);
    }

    @Operation(summary = "Criar um novo evento não programado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criar o evento")
    })
    @PostMapping("/unscheduled")
    public ResponseEntity<UnscheduledEventDto> createUnscheduledEvent(
            @Valid
            @RequestBody UnscheduledEventDto unscheduledEvent) {
        UnscheduledEventDto newUnscheduledEvent = eventService.createUnscheduledEvent(unscheduledEvent);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/resource/" + newUnscheduledEvent.getDescription())
                .body(newUnscheduledEvent);
    }

    @Operation(summary = "Buscar todos os eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado")
    })
    @GetMapping("/all")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.findAllEvents();
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
    public ResponseEntity<EventDto> getEventById(
            @Parameter(description = "ID do evento a ser buscado")
            @PathVariable Long id) {
        Optional<EventDto> eventDTO = eventService.findEventById(id);
        return eventDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
            @RequestBody ScheduledEventDto scheduledEvent) {
        try {
            Optional<?> updatedEvent = eventService.updateEventFields(id, scheduledEvent);

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
            @RequestBody UnscheduledEventDto unscheduledEvent) {
        try {
            Optional<?> updatedEvent = eventService.updateEventFields(id, unscheduledEvent);

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

    @Operation(summary = "Buscar eventos programados por data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos programados recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento programado encontrado")
    })
    @GetMapping("/scheduled/date/{date}")
    public ResponseEntity<List<ScheduledEventDto>> findScheduledEventsByDate(
            @Parameter(description = "Data do evento programado")
            @PathVariable String date) {
        List<ScheduledEventDto> events = eventService.findScheduledEventsByDate(LocalDate.parse(date));
        return events.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(events);
    }

    @Operation(summary = "Buscar eventos programados por bairro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos programados recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento programado encontrado")
    })
    @GetMapping("/scheduled/neighborhood/{neighborhood}")
    public ResponseEntity<List<ScheduledEventDto>> findScheduledEventsByNeighborhood(
            @Parameter(description = "Bairro do evento programado")
            @PathVariable String neighborhood) {
        List<ScheduledEventDto> events = eventService.findScheduledEventsByNeighborhood(neighborhood);
        return events.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(events);
    }

    @GetMapping("/unscheduled/category/{category}")
    public ResponseEntity<List<UnscheduledEventDto>> findUnscheduledEventsByCategory(
            @Parameter(description = "Categoria do evento não programado")
            @PathVariable String category) {
        List<UnscheduledEventDto> events = eventService.findUnscheduledEventsByCategory(category);
        return events.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(events);
    }

    @Operation(summary = "Buscar eventos por status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EventDto>> findEventsByStatus(
            @Parameter(description = "Status do evento")
            @PathVariable String status) {
        try {
            List<EventDto> events = eventService.findEventsByStatus(status);
            return events.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(events);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Retornar erro 400 se o status for inválido
        }
    }

    @Operation(summary = "Buscar eventos por severidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado")
    })
    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<EventDto>> findEventsBySeverity(
            @Parameter(description = "Severidade do evento")
            @PathVariable String severity) {
        List<EventDto> events = eventService.findEventsBySeverity(severity);
        return events.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(events);
    }
}
