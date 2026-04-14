package edu.co.ustavillavicencio.relationmapping.controllers.dtos.auth;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String role;
}
