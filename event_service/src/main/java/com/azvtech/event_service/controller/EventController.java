package com.azvtech.event_service.controller;

import com.azvtech.event_service.dto.CreateEventDto;
import com.azvtech.event_service.dto.ScheduledCreateEventDto;
import com.azvtech.event_service.dto.UnscheduledCreateEventDto;
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
    private static final String LOCATION_HEADER_PREFIX = "/api/resource/";
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
    public ResponseEntity<Boolean> createScheduledEvent(
            @Valid @RequestBody ScheduledCreateEventDto scheduledEvent) {
        return createEventResponse(eventService.createScheduledEvent(scheduledEvent));
    }

    @Operation(summary = "Criar um novo evento não programado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criar o evento")
    })
    @PostMapping("/unscheduled")
    public ResponseEntity<UnscheduledCreateEventDto> createUnscheduledEvent(
            @Valid @RequestBody UnscheduledCreateEventDto unscheduledEvent) {
        return createEventResponse(eventService.createUnscheduledEvent(unscheduledEvent));
    }

    private <T> ResponseEntity<T> createEventResponse(T event) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", LOCATION_HEADER_PREFIX + event)
                .body(event);
    }

    @Operation(summary = "Buscar todos os eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado")
    })
    @GetMapping("/all")
    public ResponseEntity<List<CreateEventDto>> getAllEvents() {
        List<CreateEventDto> events = eventService.getAllEvents();
        return events.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(events);
    }

    @Operation(summary = "Buscar eventos por bloqueio de vias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos recuperados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado")
    })
    @GetMapping("/roadblock/{road}")
    public ResponseEntity<List<CreateEventDto>> getEventsByRoadblock(
            @Parameter(description = "Nome da via") @PathVariable String road) {
        List<CreateEventDto> events = eventService.getEventsByRoadblock(road);
        if (events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(events);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(events);
        }
    }

    @Operation(summary = "Buscar um evento pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CreateEventDto> getEventById(
            @Parameter(description = "ID do evento a ser buscado")
            @PathVariable Long id) {
        Optional<CreateEventDto> eventDTO = eventService.getEventById(id);
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
            @RequestBody ScheduledCreateEventDto scheduledEvent) {
        return updateEventResponse(id, scheduledEvent, "EventoProgramado");
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
            @RequestBody UnscheduledCreateEventDto unscheduledEvent) {
        return updateEventResponse(id, unscheduledEvent, "EventoNaoProgramado");
    }

    private <T> ResponseEntity<?> updateEventResponse(Long id, T event, String eventType) {
        try {
            Optional<?> updatedEvent = eventService.updateEventFields(id, (CreateEventDto) event);
            return updatedEvent.isPresent()
                    ? ResponseEntity.ok(updatedEvent.get())
                    : ResponseEntity.notFound().build();
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body("O evento com o ID fornecido não é do tipo " + eventType);
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
    public ResponseEntity<List<ScheduledCreateEventDto>> findScheduledEventsByDate(
            @Parameter(description = "Data do evento programado")
            @PathVariable String date) {
        List<ScheduledCreateEventDto> events = eventService.getScheduledEventsByDate(LocalDate.parse(date));
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
    public ResponseEntity<List<ScheduledCreateEventDto>> findScheduledEventsByNeighborhood(
            @Parameter(description = "Bairro do evento programado")
            @PathVariable String neighborhood) {
        List<ScheduledCreateEventDto> events = eventService.getScheduledEventsByNeighborhood(neighborhood);
        return events.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(events);
    }

    @GetMapping("/unscheduled/category/{category}")
    public ResponseEntity<List<UnscheduledCreateEventDto>> findUnscheduledEventsByCategory(
            @Parameter(description = "Categoria do evento não programado")
            @PathVariable String category) {
        List<UnscheduledCreateEventDto> events = eventService.getUnscheduledEventsByCategory(category);
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
    public ResponseEntity<List<CreateEventDto>> findEventsByStatus(
            @Parameter(description = "Status do evento")
            @PathVariable String status) {
        return findEventsResponse(eventService.getEventsByStatus(status), "status inválido");
    }

    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<CreateEventDto>> findEventsBySeverity(
            @Parameter(description = "Severidade do evento")
            @PathVariable String severity) {
        return findEventsResponse(eventService.getEventsBySeverity(severity), "severidade inválida");
    }

    private ResponseEntity<List<CreateEventDto>> findEventsResponse(List<CreateEventDto> events, String invalidArgumentError) {
        if (!events.isEmpty()) {
            return ResponseEntity.ok(events);
        } else if (invalidArgumentError != null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
