package com.indivie.cms.repository;

import com.indivie.cms.entity.OutletProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OutletProductRepository extends JpaRepository<OutletProduct, String > {
    @Query(nativeQuery = true,
            value = "SELECT id, qty, product_id, outlet_id, qty FROM m_outlet_product WHERE product_id=:productId AND outlet_id=:outletId")
    Optional<OutletProduct> findOutletProductByProductIdAndOutletId(@Param("productId") String productId,
                                                                    @Param("outletId") String outletId);
}
