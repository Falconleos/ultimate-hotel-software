package ar.edu.utn.frmdp.ultimate_hotel_software.security;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final EmpleadoService empleadoService;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        // Buscamos el usuario en la tabla 'empleados' a través de nuestro repositorio
        EmpleadoEntity empleadoEntity = empleadoService.findByUsuario(usuario);

        // Retornamos nuestro adaptador CustomUserDetails envolviendo al usuario real
        return new CustomUserDetails(empleadoEntity);
    }

}
