package com.azvtech.mobility.urbanmobilitymanagement.repository;

import com.azvtech.mobility.urbanmobilitymanagement.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
