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

    @NotNull(message = "O número do regulamento não pode ser nulo")
    @Positive(message = "O número do regulamento deve ser um número positivo")
    private int regulationNumber;

    @NotNull(message = "A data do regulamento não pode ser nula")
    private LocalDate regulationPublicationDate;
}