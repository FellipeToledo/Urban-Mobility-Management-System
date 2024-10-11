package com.azvtech.mobility.urbanmobilitymanagement.model;

import com.azvtech.mobility.urbanmobilitymanagement.enums.Severity;
import com.azvtech.mobility.urbanmobilitymanagement.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(name = "registration_date_time", nullable = false)
    private LocalDateTime registrationDateTime;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "impact_transit")
    private String impactTransit;

    @Column(name = "road_block")
    private boolean roadBlock;

}
