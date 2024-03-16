package com.indivie.cms.repository;

import com.indivie.cms.entity.ProductSupplyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSupplyOrderRepository extends JpaRepository<ProductSupplyOrder, String> {
}
