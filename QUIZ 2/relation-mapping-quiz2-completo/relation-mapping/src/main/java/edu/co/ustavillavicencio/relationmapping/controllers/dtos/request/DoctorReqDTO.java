package edu.co.ustavillavicencio.relationmapping.controllers.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorReqDTO {

    @NotBlank(message = "El username es obligatorio")
    private String username;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "La edad mínima es 18")
    private Integer age;

    @NotBlank(message = "La especialización es obligatoria")
    private String specialization;

    @NotNull(message = "El hospital es obligatorio")
    private Long hospitalId;
}
