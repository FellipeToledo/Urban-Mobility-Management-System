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

    @NotNull(message = "A via não pode ser nula")
    @NotBlank(message = "A via não pode estar em branco")
    @Size(min = 5, max = 25, message = "A via deve ter entre 5 e 25 caracteres")
    private String road;

    @NotNull(message = "A via inicial não pode ser nula")
    @NotBlank(message = "A via inicial não pode estar em branco")
    @Size(min = 5, max = 25, message = "A via inicial deve ter entre 5 e 25 caracteres")
    private String startRoad;

    @NotNull(message = "O End Road não pode ser nulo")
    @NotBlank(message = "O End Road não pode estar em branco")
    @Size(min = 5, max = 25, message = "O End Road deve ter entre 5 e 25 caracteres")
    private String endRoad;

    @NotNull(message = "A data e a hora inicial  não podem ser nulas")
    private LocalDateTime startDateTime;

    @NotNull(message = "A data e a hora final  não podem ser nulas")
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
