package com.enigma.wmsapp.repository;

import com.enigma.wmsapp.constant.ERole;
import com.enigma.wmsapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(ERole name);
}
