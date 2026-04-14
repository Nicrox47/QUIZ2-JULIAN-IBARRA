package edu.co.ustavillavicencio.relationmapping.controllers.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HospitalReqDTO {

    @NotBlank(message = "El nombre del hospital es obligatorio")
    private String name;
}
