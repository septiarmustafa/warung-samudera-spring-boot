package com.enigma.wmsapp.service.impl;

import com.enigma.wmsapp.entity.Role;
import com.enigma.wmsapp.repository.RoleRepository;
import com.enigma.wmsapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public Role getOrSave(Role role) {
        Role optionalRole = roleRepository.findByName(role.getName());
        if (optionalRole != null) return optionalRole;
        return roleRepository.save(role);
    }
}
