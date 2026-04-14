package edu.co.ustavillavicencio.relationmapping.controllers;

import edu.co.ustavillavicencio.relationmapping.controllers.dtos.auth.AuthRequest;
import edu.co.ustavillavicencio.relationmapping.controllers.dtos.auth.AuthResponse;
import edu.co.ustavillavicencio.relationmapping.controllers.dtos.auth.SignupRequest;
import edu.co.ustavillavicencio.relationmapping.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        userService.signup(request.getUsername(), request.getPassword(), request.getRole());
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(userService.login(request.getUsername(), request.getPassword()));
    }
}
