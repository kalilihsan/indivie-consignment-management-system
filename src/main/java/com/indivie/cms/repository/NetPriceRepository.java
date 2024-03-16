package com.indivie.cms.repository;

import com.indivie.cms.entity.NetCost;
import com.indivie.cms.entity.NetPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetPriceRepository extends JpaRepository<NetPrice, String > {
}
