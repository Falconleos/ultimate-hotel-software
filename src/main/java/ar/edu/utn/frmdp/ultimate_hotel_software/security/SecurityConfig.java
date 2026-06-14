package ar.edu.utn.frmdp.ultimate_hotel_software.security;

import ar.edu.utn.frmdp.ultimate_hotel_software.security.handler.JwtAuthenticationEntryPoint;
import ar.edu.utn.frmdp.ultimate_hotel_software.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;

    /**
     * Define la cadena de filtros de seguridad y las reglas de acceso a las URLs.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Deshabilitamos CSRF (Cross-Site Request Forgery) porque los tokens JWT ya nos protegen de esto
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configuramos qué rutas son libres y cuáles requieren candado
                .authorizeHttpRequests(auth -> auth
                        // Permitimos libre acceso a los endpoints de autenticación (login, registro)
                        .requestMatchers("/api/auth/**").permitAll()
                        // Cualquier otra petición al backend del hotel requerirá estar autenticado
                        .anyRequest().authenticated()
                )

                // 3. Configuramos la gestión de sesiones como "STATELESS" (sin estado en servidor)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Asignamos nuestro manejador de errores personalizado para los "rebotes" de seguridad
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                // 5. Conectamos la estrategia de autenticación (quién busca al usuario y cómo compara contraseñas)
                .authenticationProvider(authenticationProvider())

                // 6. ¡EL ACROPILAMIENTO CRUCIAL!: Metemos nuestro filtro JWT antes del filtro de login por defecto de Spring
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * El proveedor de autenticación encargado de conectar el UserDetailsService
     * con el encriptador de contraseñas de forma directa.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(
                userDetailsService
        );
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Bean del encriptador oficial para las contraseñas en la base de datos (BCrypt).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Administrador de autenticación nativo de Spring que usaremos en los controladores
     * para verificar las credenciales al iniciar sesión.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
