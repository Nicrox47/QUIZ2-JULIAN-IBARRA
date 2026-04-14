package edu.co.ustavillavicencio.relationmapping.services;

import edu.co.ustavillavicencio.relationmapping.config.jwt.JwtUtils;
import edu.co.ustavillavicencio.relationmapping.controllers.dtos.auth.AuthResponse;
import edu.co.ustavillavicencio.relationmapping.entities.UserApp;
import edu.co.ustavillavicencio.relationmapping.exception.BusinessRuleException;
import edu.co.ustavillavicencio.relationmapping.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public void signup(String username, String password, String role) {

        boolean passwordValid = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
                .matcher(password)
                .find();

        log.info("Validando contraseña para el usuario '{}'", username);

        if (userRepository.findByUsername(username).isPresent()) {
            throw new BusinessRuleException("El usuario ya existe");
        }

        if (!passwordValid) {
            throw new BusinessRuleException("La contraseña no cumple con los requisitos de seguridad");
        }

        String normalizedRole = role == null ? "" : role.replace("ROLE_", "").trim().toUpperCase();

        if (userRepository.countByRole("ADMIN") >= 1 && normalizedRole.equals("ADMIN")) {
            throw new BusinessRuleException("Ya existe un administrador registrado");
        }

        if (!normalizedRole.equals("DOCTOR") && !normalizedRole.equals("ADMIN")) {
            throw new BusinessRuleException("Rol no válido. Solo se permite ADMIN o DOCTOR");
        }

        UserApp user = new UserApp();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(normalizedRole);

        userRepository.save(user);
        log.info("Usuario '{}' registrado exitosamente con el rol '{}'", username, normalizedRole);
    }

    public AuthResponse login(String username, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );

        String token = jwtUtils.generateToken(userDetailsService.loadUserByUsername(username));
        return new AuthResponse(token);

    }
}
