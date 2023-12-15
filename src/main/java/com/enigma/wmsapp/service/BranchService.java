package com.enigma.wmsapp.service;

import com.enigma.wmsapp.dto.response.BranchResponse;
import com.enigma.wmsapp.entity.Branch;

import java.util.List;

public interface BranchService {
    BranchResponse createBranch (Branch branch);
    BranchResponse getByIdBranch (String id);
    List<BranchResponse> getAllBranch();
    BranchResponse updateBranch (Branch branch);
    void deleteBranch (String id);
}
