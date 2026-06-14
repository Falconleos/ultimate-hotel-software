package ar.edu.utn.frmdp.ultimate_hotel_software.controller;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.LoginRequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.AuthenticationResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    /**
     * Endpoint para registrar nuevos usuarios.
     * URL: http://localhost:8080/api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody EmpleadoDTORequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Endpoint para iniciar sesión.
     * URL: http://localhost:8080/api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
