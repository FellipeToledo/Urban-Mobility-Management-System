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

@AllArgsConstructor
@Entity
@Table(name = "scheduled_event")
public class ScheduledEvent extends Event{

    @Column(nullable = false , name = "regulation_number")
    private int regulationNumber;

    @Column(nullable = false , name = "regulation_pub_date")
    private LocalDate regulationPublicationDate;

    @Column(nullable = false , name = "regulation_validity")
    private LocalDate regulationValidity;

}
