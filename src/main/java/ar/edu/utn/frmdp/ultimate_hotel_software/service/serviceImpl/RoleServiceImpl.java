package ar.edu.utn.frmdp.ultimate_hotel_software.service.serviceImpl;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.EmpleadoNoEncontradoException;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.InvalidNameException;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.RolNoEncontradoException;
import ar.edu.utn.frmdp.ultimate_hotel_software.exception.RoleDuplicadoException;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Role;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.RoleRequestDto;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.RoleResponseDto;
import ar.edu.utn.frmdp.ultimate_hotel_software.repository.RoleRepository;
import ar.edu.utn.frmdp.ultimate_hotel_software.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    //1. Buscar rol por ID
    //1.1 Devuelve entidad
    @Override
    public Role findEntityByName(RoleType name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new InvalidNameException("Rol no encontrado: " + name));
    }

    //1.2. Devuelve DTO Response
    @Override
    public RoleResponseDto findById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RolNoEncontradoException("Rol no encontrado"));

        return toResponse(role);
    }

    //2. Listar roles
    @Override
    public List<RoleResponseDto> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }


    //3. Crear rol
    @Override
    public RoleResponseDto create(RoleRequestDto request) {

        if (roleRepository.existsByName(request.name())) {
            throw new RoleDuplicadoException("El rol ya existe");
        }

        Role role = Role.builder()
                .name(request.name())
                .build();

        Role savedRole = roleRepository.save(role);

        return toResponse(savedRole);
    }

    //Mapper a DTO Response
    private RoleResponseDto toResponse(Role role) {
        return new RoleResponseDto(
                role.getId(),
                role.getName()
        );
    }
}