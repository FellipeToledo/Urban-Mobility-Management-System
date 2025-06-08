package com.azvtech.event_service.dto.request;

import com.azvtech.event_service.enums.Agency;
import com.azvtech.event_service.enums.Severity;
import com.azvtech.event_service.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.ElementCollection;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class EventRequest {

    @NotNull(message = "As lista de agências envolvidas não podem ser nulas")
    @NotEmpty(message = "A lista de agências envolvidas não pode ser vazia")
    private List<@Valid Agency> involvedAgencies;

    @NotNull(message = "A categoria não podem ser nulos")
    @NotEmpty(message = "A categoria não pode ser vazia")
    private String category;

    @NotNull(message = "A descrição não pode ser nula")
    @Size(min = 5, max = 100, message = "A descrição deve ter entre 5 e 100 caracteres")
    @NotBlank(message = "A descrição não pode estar em branco")
    private String description;

    @NotNull(message = "O bairro não pode ser nulo")
    @NotBlank(message = "O bairro não pode estar em branco")
    @Size(min = 3, max = 25, message = "O bairro deve ter entre 5 e 25 caracteres")
    private String neighborhood;

    @NotNull(message = "A criticidade não pode ser nula")
    private Severity severity;

    @NotNull(message = "Status não pode ser nulo")
    private Status status;

    @ElementCollection
    private List<@Valid RoadblockRequest> roadblocks;

    public void setDescription(String description) {
        this.description = description.trim().replaceAll("\\s+", " ");
    }

}
