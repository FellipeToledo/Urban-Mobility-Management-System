package com.azvtech.event_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduledEventDto extends EventDto{

    @NotNull
    private String regulationId;

    @NotNull
    private LocalDate regulationDate;

    @NotNull
    private LocalDateTime roadblockDate;

    @NotNull
    private String neighborhood;
}
