package com.azvtech.event_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

@AllArgsConstructor
@Entity
@Table(name = "scheduled_event")
public class ScheduledEvent extends Event{

    @Column(nullable = false)
    private int regulationNumber;

    @Column(nullable = false)
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate regulationPublicationDate;

}
