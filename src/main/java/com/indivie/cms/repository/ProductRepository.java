package com.indivie.cms.repository;

import com.indivie.cms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String > {
    @Query(nativeQuery = true,
    value = "SELECT * FROM m_product WHERE supplier_id=:supplierId")
    Optional<List<Product>> findAllBySupplierId(@Param("supplierId") String supplierId);
}
