package com.enigma.wmsapp.service;

import com.enigma.wmsapp.dto.request.AdminRequest;
import com.enigma.wmsapp.dto.response.AdminResponse;
import com.enigma.wmsapp.entity.Admin;

import java.util.List;

public interface AdminService {
    AdminResponse createAdmin (Admin admin);
    AdminResponse getByIdAdmin (String id);
    List<AdminResponse> getAllAdmin();
    AdminResponse updateAdmin (AdminRequest adminRequest);
    void deleteAdmin (String id);
}
