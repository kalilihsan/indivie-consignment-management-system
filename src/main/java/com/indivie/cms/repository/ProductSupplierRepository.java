package com.indivie.cms.repository;

import com.indivie.cms.entity.ProductSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, String> {
    @Query(nativeQuery = true,
    value = "SELECT * FROM m_supplier WHERE name=:supplierName")
    Optional<List<ProductSupplier>> findSupplierByName(@Param("supplierName") String supplierName);
}
