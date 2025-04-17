package com.azvtech.event_service.dto.request;

import com.azvtech.event_service.enums.Cause;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnscheduledEventRequest extends EventRequest {

    @NotNull(message = "A causa não pode ser nula")
    @JsonProperty("cause")
    private Cause cause;
}
