package com.azvtech.event_service.repository;

import com.azvtech.event_service.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Modifying
    @Query("DELETE FROM Event e WHERE e.id IN :ids")
    int deleteAllByIdInBatch(@Param("ids") List<Long> ids);
}

