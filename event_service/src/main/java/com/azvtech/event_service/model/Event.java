package com.azvtech.event_service.model;

import com.azvtech.event_service.enums.Severity;
import com.azvtech.event_service.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "events")
@Inheritance(strategy = InheritanceType.JOINED)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(name = "registration_date_time", nullable = false)
    private LocalDateTime registrationDateTime;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @ElementCollection
    private List<Roadblock> roadblocks;

}
