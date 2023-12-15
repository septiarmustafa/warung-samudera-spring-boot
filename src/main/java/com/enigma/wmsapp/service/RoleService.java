package com.enigma.wmsapp.service;

import com.enigma.wmsapp.entity.Role;

public interface RoleService {
    Role getOrSave (Role role);
}
