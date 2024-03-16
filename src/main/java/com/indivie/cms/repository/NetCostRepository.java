package com.indivie.cms.repository;

import com.indivie.cms.entity.NetCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetCostRepository extends JpaRepository<NetCost, String> {
}
