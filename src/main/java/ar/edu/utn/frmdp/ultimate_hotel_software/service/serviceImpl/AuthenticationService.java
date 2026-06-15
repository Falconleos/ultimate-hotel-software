package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.mapper.EmpleadoMapper;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.EmpleadoDTORequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.LoginRequest;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.AuthenticationResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.EmpleadoDTOResponse;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.security.CustomUserDetails;
import ar.edu.utn.frmdp.ultimate_hotel_software.security.jwt.JwtService;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmpleadoService empleadoService;
    private final EmpleadoMapper empleadoMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    /**
     * Lógica para registrar un nuevo usuario en el sistema del hotel.
     */
    public AuthenticationResponse register(EmpleadoDTORequest request) {

        //VALIDACION SI QUIERE REGISTRAR UN ADMINISTRATIVO
        //Y NO ES ADMINISTRATIVO LANZA UNA EXCEPCION

        // Verificación de rol si el nuevo usuario es ADMINISTRATIVO
        if (RoleType.ADMINISTRATIVO.equals(request.getRoleType())) {
            var auth = SecurityContextHolder.getContext().getAuthentication();

            // Verificamos si el usuario actual tiene el rol necesario
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMINISTRATIVO"));

            if (!isAdmin) {
                throw new AccessDeniedException("Solo un administrador puede crear otro administrador.");
            }
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        EmpleadoDTOResponse empleadoDTOResponse = empleadoService.createEmpleado(request);
        EmpleadoEntity empleadoEntity = empleadoService.findEntityById(empleadoDTOResponse.getId());

        // 3. Converters el EmpleadoEntity en CustomUserDetails para que JwtService lo pueda procesar
        CustomUserDetails userDetails = new CustomUserDetails(empleadoEntity);
        String jwtToken = jwtService.generateAccessToken(userDetails);

        // 4. Retornamos la respuesta con el token envuelto
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Lógica para validar las credenciales de un usuario e iniciar sesión.
     */
    public AuthenticationResponse login(LoginRequest request) {
        // 1. El AuthenticationManager valida contra la base de datos de manera automática
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. Si las credenciales son correctas, buscamos la entidad en MySQL
        EmpleadoEntity empleadoEntity = empleadoService.findByUsuario(request.getUsername());

        // 3. Convertimos a CustomUserDetails y generamos su token de sesión fresquito
        CustomUserDetails userDetails = new CustomUserDetails(empleadoEntity);
        String jwtToken = jwtService.generateAccessToken(userDetails);

        // 4. Devolvemos el token al frontend
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
