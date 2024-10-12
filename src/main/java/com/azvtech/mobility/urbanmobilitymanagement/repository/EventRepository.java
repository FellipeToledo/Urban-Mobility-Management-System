package com.azvtech.mobility.urbanmobilitymanagement.repository;

import com.azvtech.mobility.urbanmobilitymanagement.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
