package com.azvtech.event_service.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scheduled_event")
public class ScheduledEvent extends Event{

    @Column(nullable = false)
    private Integer regulationNumber;

    @Column(nullable = false)
    private LocalDate regulationDate;

    @Column(nullable = false)
    private String neighborhood;
}
