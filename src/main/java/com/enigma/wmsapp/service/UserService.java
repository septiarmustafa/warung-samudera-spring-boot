package com.enigma.wmsapp.service;

import com.enigma.wmsapp.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadUserByUserId (String id);
}
