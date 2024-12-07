package com.azvtech.event_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scheduled_event")
public class ScheduledEvent extends Event{

    @Column(nullable = false)
    private int regulationNumber;

    @Column(nullable = false)
    private LocalDate regulationDate;

}
