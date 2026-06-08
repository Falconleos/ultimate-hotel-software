package ar.edu.utn.frmdp.ultimate_hotel_software.models.personas;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.enums.Turno;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "empleados")

public class EmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DatosPersonalesEntity datosPersona;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Turno turno;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @Column(nullable = false, unique = true, length = 50)
    private String usuario;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, updatable = false)
    private LocalDate fechaIngreso;

    @Column(nullable = false)
    private Boolean activo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
