package com.enigma.wmsapp.service.impl;


import com.enigma.wmsapp.dto.request.AdminRequest;
import com.enigma.wmsapp.dto.response.AdminResponse;
import com.enigma.wmsapp.entity.Admin;
import com.enigma.wmsapp.repository.AdminRepository;
import com.enigma.wmsapp.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public AdminResponse createAdmin(Admin admin) {
        Admin adminSave = adminRepository.saveAndFlush(admin);
        return AdminResponse.builder()
                .id(adminSave.getId())
                .name(adminSave.getName())
                .phoneNumber(adminSave.getPhoneNumber())
                .build();
    }

    @Override
    public AdminResponse getByIdAdmin(String id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin == null) {
            return null;
        }
       return AdminResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .phoneNumber(admin.getPhoneNumber())
                .build();
    }
    @Override
    public List<AdminResponse> getAllAdmin() {
        List<Admin> adminList = adminRepository.findAll();
        return adminList.stream().map(
                        admin -> AdminResponse.builder()
                                .id(admin.getId())
                                .name(admin.getName())
                                .phoneNumber(admin.getPhoneNumber())
                                .build())
                .collect(Collectors.toList());
    }
    @Override
    public AdminResponse updateAdmin(AdminRequest adminRequest) {
        Admin admin = adminRepository.findById(adminRequest.getId()).orElse(null);
        if (admin == null) return null;
        admin = Admin.builder()
                .id(adminRequest.getId())
                .name(adminRequest.getName())
                .phoneNumber(adminRequest.getPhoneNumber())
                .build();
        adminRepository.save(admin);
        return AdminResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .phoneNumber(admin.getPhoneNumber())
                .build();
    }
    @Override
    public void deleteAdmin(String id) {
        adminRepository.deleteById(id);
    }
}
