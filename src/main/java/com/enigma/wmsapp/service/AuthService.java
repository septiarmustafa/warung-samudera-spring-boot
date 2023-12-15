package com.enigma.wmsapp.service;

import com.enigma.wmsapp.dto.request.AuthRequest;
import com.enigma.wmsapp.dto.response.LoginResponse;
import com.enigma.wmsapp.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerAdmin (AuthRequest authRequest);
    LoginResponse login(AuthRequest authRequest);
}
