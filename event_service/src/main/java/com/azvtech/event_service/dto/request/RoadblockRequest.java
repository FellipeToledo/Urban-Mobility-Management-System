package com.azvtech.event_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Fellipe Toledo
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoadblockRequest {

    @NotNull(message = "A via não pode ser nula")
    @NotBlank(message = "A via não pode estar em branco")
    @Size(min = 5, max = 25, message = "A via deve ter entre 5 e 25 caracteres")
    private String road;

    @NotNull(message = "A especificação não pode ser nula")
    @NotBlank(message = "A especificação não pode estar em branco")
    @Size(min = 5, max = 500, message = "A especificação deve ter entre 5 e 500 caracteres")
    private String specification;

    private LocalDate date;

    private LocalDateTime dateTime;

    public void setRoad(String road) {
        this.road = road.trim().replaceAll("\\s+", " ");
    }
}
