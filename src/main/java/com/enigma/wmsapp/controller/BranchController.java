package com.enigma.wmsapp.controller;

import com.enigma.wmsapp.constant.AppPath;
import com.enigma.wmsapp.dto.response.BranchResponse;
import com.enigma.wmsapp.dto.response.CommonResponse;
import com.enigma.wmsapp.entity.Branch;
import com.enigma.wmsapp.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.BRANCH)
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<?> createBranch(@RequestBody Branch branch) {
        BranchResponse branchResponse = branchService.createBranch(branch);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<BranchResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new branch")
                        .data(branchResponse).build());
    }

    @GetMapping(value = AppPath.ID_BRANCH)
    public ResponseEntity<?> getByIdBranch ( @PathVariable String id_branch){
        BranchResponse branchResponse = branchService.getByIdBranch(id_branch);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get branch")
                        .data(branchResponse).build());
    }

    @GetMapping
    public ResponseEntity<?> getAll (){
        List<BranchResponse> branchList = branchService.getAllBranch();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all")
                        .data(branchList).build());
    }

    @PutMapping
    public ResponseEntity<?> updateBranch (@RequestBody Branch branch) {
        BranchResponse branches = branchService.updateBranch(branch);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update branch")
                        .data(branches).build());
    }

    @DeleteMapping(value = AppPath.ID_BRANCH)
    public ResponseEntity<?> deleteBranch (@PathVariable String id_branch){
        branchService.deleteBranch(id_branch);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete branch")
                        .data("OK").build());
    }

}
