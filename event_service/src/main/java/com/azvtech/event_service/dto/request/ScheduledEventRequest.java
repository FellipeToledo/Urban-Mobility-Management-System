package com.azvtech.event_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ScheduledEventRequest extends EventRequest {

    @NotNull(message = "O número do regulamento não pode ser nulo")
    @Positive(message = "O número do regulamento deve ser um número positivo")
    private Integer regulationNumber;

    @NotNull(message = "A data do regulamento não pode ser nula")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate regulationPublicationDate;
}