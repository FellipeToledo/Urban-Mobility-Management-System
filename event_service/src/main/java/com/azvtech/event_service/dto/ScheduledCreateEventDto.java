package com.azvtech.event_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
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
public class ScheduledCreateEventDto extends CreateEventDto {

    @NotNull(message = "Regulation Number cannot be null")
    @Positive(message = "Regulation Number must be a positive number")
    private int regulationNumber;

    @NotNull(message = "Regulation date cannot be null")
    private LocalDate regulationPublicationDate;
}