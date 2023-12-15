package com.enigma.wmsapp.repository;

import com.enigma.wmsapp.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, String> {
}
