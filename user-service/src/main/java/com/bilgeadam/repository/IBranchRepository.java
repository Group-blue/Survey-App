package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBranchRepository extends JpaRepository<Branch, Long> {
}
