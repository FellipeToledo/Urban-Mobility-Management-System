package com.azvtech.event_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Fellipe Toledo
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoadblockDto {

    @NotNull(message = "Road cannot be null")
    @NotBlank(message = "Road cannot be blank")
    @Size(min = 5, max = 25, message = "Road must be between 5 and 25 characters")
    private String road;

    @NotNull(message = "Start Road cannot be null")
    @NotBlank(message = "Start Road cannot be blank")
    @Size(min = 5, max = 25, message = "Start Road must be between 5 and 25 characters")
    private String startRoad;

    @NotNull(message = "End Road cannot be null")
    @NotBlank(message = "End Road cannot be blank")
    @Size(min = 5, max = 25, message = "End Road must be between 5 and 25 characters")
    private String endRoad;

    @NotNull(message = "Start Date Time cannot be null")
    private LocalDateTime startDateTime;

    @NotNull(message = "End Date Time cannot be null")
    private LocalDateTime endDateTime;

    public void setRoad(String road) {
        this.road = road.trim().replaceAll("\\s+", " ");
    }
    public void setStartRoad(String startRoad) {
        this.startRoad = startRoad.trim().replaceAll("\\s+", " ");
    }
    public void setEndRoad(String endRoad) {
        this.endRoad = endRoad.trim().replaceAll("\\s+", " ");
    }

}
