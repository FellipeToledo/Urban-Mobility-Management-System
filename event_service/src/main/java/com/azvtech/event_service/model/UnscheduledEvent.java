package com.azvtech.event_service.model;

import com.azvtech.event_service.enums.Cause;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

@AllArgsConstructor
@Entity
@Table(name = "unscheduled_event")
public class UnscheduledEvent extends Event {

    @Column(nullable = false)
    private Cause cause;

}
