package edu.co.ustavillavicencio.relationmapping.controllers.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResDTO {
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private Integer age;
    private String specialization;
    private HospitalResDTO hospital;
}
