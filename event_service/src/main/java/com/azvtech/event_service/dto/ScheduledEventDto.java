package com.azvtech.event_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduledEventDto extends EventDto{

    @NotNull(message = "Regulation Number cannot be null")
    private Integer regulationNumber;

    @NotNull(message = "Regulation date cannot be null")
    private LocalDate regulationDate;

    @NotNull(message = "Neighborhood cannot be null")
    private String neighborhood;
}
