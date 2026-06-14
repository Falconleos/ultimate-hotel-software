package ar.edu.utn.frmdp.ultimate_hotel_software.security;

import ar.edu.utn.frmdp.ultimate_hotel_software.models.personas.EmpleadoEntity;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CustomUserDetails(EmpleadoEntity empleadoEntity) implements UserDetails {


    private static final String ROLE_PREFIX = "ROLE_";

    // método extra para obtener el ID de la base de datos
    public Long getId(){
        return empleadoEntity.getId();
    }

    // Spring Security maneja los permisos como "Authorities".
    @Override
    public Collection<? extends GrantedAuthority>getAuthorities(){
        return empleadoEntity.getRoles()
                .stream()
                .map( role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getName().name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return empleadoEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return empleadoEntity.getUsuario();
    }

    // --- CORRECCIÓN CRÍTICA PARA EL RECORD ---
    // Forzamos que devuelvan true para que la cuenta se considere válida y activa.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
