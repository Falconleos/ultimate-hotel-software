package ar.edu.utn.frmdp.ultimate_hotel_software.security.handler;

import jakarta.persistence.Column;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // 1. Seteamos el estado HTTP como 401 (No Autorizado)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 2. Le avisamos al cliente que le respondemos con un JSON en texto UTF-8
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 3. Escribimos el cuerpo del JSON que viajará al frontend
        String jsonResponse = String.format(
                "{\n" +
                        "  \"status\": 401,\n" +
                        "  \"error\": \"No autorizado\",\n" +
                        "  \"message\": \"%s\"\n" +
                        "}",
                authException.getMessage()
        );

        // 4. enviamos el JSON de vuelta al auto que quiso entrar sin permiso
        response.getWriter().write(jsonResponse);
    }

}
