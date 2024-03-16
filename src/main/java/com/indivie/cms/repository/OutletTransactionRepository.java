package com.indivie.cms.repository;

import com.indivie.cms.entity.OutletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutletTransactionRepository extends JpaRepository<OutletTransaction, String > {
}
