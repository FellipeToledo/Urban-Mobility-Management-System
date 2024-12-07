package com.azvtech.event_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnscheduledCreateEventDto extends CreateEventDto {

    @NotNull(message = "Category cannot be null")
    @NotBlank(message = "Category cannot be blank")
    @Size(min = 3, max = 15, message = "Category must be between 3 and 15 characters")
    private String category;
}
