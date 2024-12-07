package com.azvtech.event_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unscheduled_event")
public class UnscheduledEvent extends Event {

    @Column(nullable = false)
    private String category;

}
