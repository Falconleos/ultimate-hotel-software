package ar.edu.utn.frmdp.ultimate_hotel_software.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // Inyecta el UserDetailServiceImpl automáticamente

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {


        // 1. Extraemos el Header llamado "Authorization" de la petición
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;



        // 2. Si el header está vacío o no empieza con "Bearer ", ignoramos el filtro y dejamos pasar la petición.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. El formato del header es "Bearer texto_del_token...". Cortamos los primeros 7 caracteres para quedarnos solo con el token string.
        jwt = authHeader.substring(7);



        System.out.println("Token recibido: " + jwt);
        username = jwtService.extractUsername(jwt);
        System.out.println("Username extraído del token: " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            System.out.println("Usuario encontrado en BD: " + userDetails.getUsername());

            if (jwtService.isTokenValid(jwt, userDetails)) {
                System.out.println("Token válido: Autenticando...");
                // ... resto del código
            } else {
                System.out.println("¡TOKEN INVÁLIDO!");
            }
        }



        // 4. Usamos el servicio para extraer el nombre de usuario de adentro del token
       // username = jwtService.extractUsername(jwt);

        // 5. Si encontramos un username y el usuario NO está ya autenticado en el contexto de Spring...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Buscamos al usuario en la base de datos usando el puente (UserDetailsService)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 6. Si el token es matemáticamente válido y pertenece a ese usuario...
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Creamos el objeto de autenticación que entiende Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // No pasamos contraseña porque ya está validado por token
                        userDetails.getAuthorities() // Le pasamos sus roles (ej: ROLE_USER, ROLE_ADMIN)
                );

                // Le agregamos detalles técnicos de la petición (como la IP de origen)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Seteamos al usuario en el contexto de seguridad global de Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 7. Continuamos hacia el Controller o hacia el siguiente filtro
        filterChain.doFilter(request, response);
    }

}
