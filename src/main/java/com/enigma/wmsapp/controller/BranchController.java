package com.enigma.wmsapp.controller;

import com.enigma.wmsapp.constant.AppPath;
import com.enigma.wmsapp.dto.response.BranchResponse;
import com.enigma.wmsapp.dto.response.CommonResponse;
import com.enigma.wmsapp.entity.Branch;
import com.enigma.wmsapp.service.BranchService;
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
@RequestMapping(AppPath.BRANCH)
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Branch branch) {
        BranchResponse branchResponse = branchService.createBranch(branch);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<BranchResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new product")
                        .data(branchResponse).build());
    }
}
