package ru.gb.fitnessclub.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.fitnessclub.authservice.entities.Role;
import ru.gb.fitnessclub.authservice.exception.ResourceNotFoundException;
import ru.gb.fitnessclub.authservice.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").orElseThrow(()-> new ResourceNotFoundException("RoleSevice:getUserRole - не верный запрос"));
    }
}