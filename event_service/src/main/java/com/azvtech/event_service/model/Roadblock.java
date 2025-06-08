package com.azvtech.event_service.model;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Embeddable
public class Roadblock {

    @NotNull(message = "Road cannot be null")
    private String road;

    @NotNull(message = "Specification cannot be null")
    private String specification;

    private LocalDate date;

    private LocalDateTime dateTime;
}
