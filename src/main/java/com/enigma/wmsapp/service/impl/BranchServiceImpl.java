package com.enigma.wmsapp.service.impl;

import com.enigma.wmsapp.dto.response.BranchResponse;
import com.enigma.wmsapp.entity.Branch;
import com.enigma.wmsapp.repository.BranchRepository;
import com.enigma.wmsapp.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    public BranchResponse createBranch(Branch branch) {
        Branch branches = branchRepository.saveAndFlush(branch);
        return BranchResponse.builder()
                .branchId(branches.getBranchId())
                .branchCode(branches.getBranchCode())
                .branchName(branches.getBranchName())
                .address(branches.getAddress())
                .phoneNumber(branches.getPhoneNumber())
                .build();
    }
    @Override
    public BranchResponse getByIdBranch(String id) {
        Branch branch = branchRepository.findById(id).orElse(null);

        if (branch == null) {
            return null;
        }
        return BranchResponse.builder()
                .branchId(branch.getBranchId())
                .branchCode(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .phoneNumber(branch.getPhoneNumber())
                .build();
    }

    @Override
    public List<BranchResponse> getAllBranch() {
        List<Branch> list = branchRepository.findAll();
        return list.stream().map(
                        branch -> BranchResponse.builder()
                                .branchId(branch.getBranchId())
                                .branchCode(branch.getBranchCode())
                                .branchName(branch.getBranchName())
                                .address(branch.getAddress())
                                .phoneNumber(branch.getPhoneNumber())
                                .build())
                .collect(Collectors.toList());
    }

    @Override
    public BranchResponse updateBranch(Branch branch) {
        Branch branches = branchRepository.findById(branch.getBranchId()).orElse(null);
        if (branches == null) {
            return null;
        }
        branchRepository.save(branches);
        return BranchResponse.builder()
                .branchId(branch.getBranchId())
                .branchCode(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .phoneNumber(branch.getPhoneNumber())
                .build();

    }
    @Override
    public void deleteBranch(String id) {
        branchRepository.deleteById(id);
    }
}
