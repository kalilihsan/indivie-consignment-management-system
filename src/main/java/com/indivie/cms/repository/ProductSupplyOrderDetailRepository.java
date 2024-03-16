package com.indivie.cms.repository;

import com.indivie.cms.entity.ProductSupplyOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSupplyOrderDetailRepository extends JpaRepository<ProductSupplyOrderDetail, String> {
}
