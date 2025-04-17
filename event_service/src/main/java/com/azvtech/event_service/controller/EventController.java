package com.azvtech.event_service.controller;

import com.azvtech.event_service.dto.request.EventRequest;
import com.azvtech.event_service.dto.request.ScheduledEventRequest;
import com.azvtech.event_service.dto.request.UnscheduledEventRequest;
import com.azvtech.event_service.dto.response.RoadblockResponse;
import com.azvtech.event_service.dto.response.detail.EventDetailResponse;
import com.azvtech.event_service.dto.response.list.EventListResponse;
import com.azvtech.event_service.enums.Cause;
import com.azvtech.event_service.exception.ErrorResponse;
import com.azvtech.event_service.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {
    private static final String LOCATION_HEADER_PREFIX = "/api/resource/";
    private final EventService eventService;

    @PostMapping("/scheduled")
    @Operation(summary = "Create a new scheduled event")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Boolean> createScheduledEvent(
            @Valid @RequestBody ScheduledEventRequest request) {
        return buildCreatedResponse(eventService.createScheduledEvent(request));
    }

    @PostMapping("/unscheduled")
    @Operation(summary = "Create a new unscheduled event")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<UnscheduledEventRequest> createUnscheduledEvent(
            @Valid @RequestBody UnscheduledEventRequest request) {
        return buildCreatedResponse(eventService.createUnscheduledEvent(request));
    }

    @GetMapping
    @Operation(summary = "Get all events")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Events retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No events found")
    })
    public ResponseEntity<List<EventListResponse>> getAllEvents() {
        List<EventListResponse> events = eventService.getAllEvents();
        return buildListResponse(events);
    }

    @GetMapping("/roadblock/{road}")
    @Operation(summary = "Get events by roadblock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Events retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No events found for this roadblock")
    })
    public ResponseEntity<List<EventRequest>> getEventsByRoadblock(
            @Parameter(description = "Road name") @PathVariable String road) {
        List<EventRequest> events = eventService.getEventsByRoadblock(road);
        return buildListResponse(events);
    }

    @GetMapping("/roadblocks")
    @Operation(summary = "Get all roadblocks")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roadblocks retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No roadblocks found")
    })
    public ResponseEntity<List<RoadblockResponse>> getAllRoadblocks() {
        List<RoadblockResponse> roadblocks = eventService.getAllRoadblocks();
        return buildListResponse(roadblocks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event details by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event details retrieved"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public ResponseEntity<EventDetailResponse> getEventDetail(
            @Parameter(description = "Event ID") @PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventDetail(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event updated"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EventRequest> updateEvent(
            @Parameter(description = "Event ID") @PathVariable Long id,
            @Valid @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an event")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public ResponseEntity<Void> deleteEvent(
            @Parameter(description = "Event ID") @PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    // Métodos auxiliares privados
    private <T> ResponseEntity<T> buildCreatedResponse(T body) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", LOCATION_HEADER_PREFIX + body)
                .body(body);
    }

    private <T> ResponseEntity<List<T>> buildListResponse(List<T> list) {
        return list.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(list);
    }
}
