package com.azvtech.event_service.dto;

import com.azvtech.event_service.enums.Severity;
import com.azvtech.event_service.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.ElementCollection;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {

    @NotNull(message = "Description cannot be null")
    @Size(min = 5, max = 25, message = "Description must be between 5 and 25 characters")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    private Severity severity;

    private Status status;

    @ElementCollection
    @NotNull(message = "Roadblocks cannot be null")
    private List<@Valid RoadblockDto> roadblocks;

    public void setDescription(String description) {
        this.description = description.trim().replaceAll("\\s+", " ");
    }

}
