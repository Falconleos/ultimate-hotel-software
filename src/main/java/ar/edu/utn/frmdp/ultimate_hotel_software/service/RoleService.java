package ar.edu.utn.frmdp.ultimate_hotel_software.service;

import ar.edu.utn.frmdp.ultimate_hotel_software.enums.RoleType;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.Role;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.requests.RoleRequestDto;
import ar.edu.utn.frmdp.ultimate_hotel_software.models.dto.response.RoleResponseDto;

import java.util.List;

public interface RoleService {

    List<RoleResponseDto> findAll();

    RoleResponseDto findById(Long id);

    RoleResponseDto create(RoleRequestDto request);

    Role findEntityByName(RoleType name);
}