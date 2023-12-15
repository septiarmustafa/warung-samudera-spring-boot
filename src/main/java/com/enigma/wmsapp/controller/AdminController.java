package com.enigma.wmsapp.controller;

import com.enigma.wmsapp.constant.AppPath;
import com.enigma.wmsapp.dto.response.AdminResponse;
import com.enigma.wmsapp.dto.response.CommonResponse;
import com.enigma.wmsapp.entity.Admin;
import com.enigma.wmsapp.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.ADMIN)
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(@RequestBody Admin admin) {
        AdminResponse adminResponse = adminService.createAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<AdminResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new product")
                        .data(adminResponse).build());
    }
}
