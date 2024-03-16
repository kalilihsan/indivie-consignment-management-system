package com.indivie.cms.repository;

import com.indivie.cms.entity.OutletTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutletTransactionDetailRepository extends JpaRepository<OutletTransactionDetail, String> {
}
