package com.indivie.cms.repository;

import com.indivie.cms.entity.ProductTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTransferRepository extends JpaRepository<ProductTransfer,String> {
}
