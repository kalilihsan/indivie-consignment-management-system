package com.indivie.cms.repository;

import com.indivie.cms.entity.OutletProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OutletProductRepository extends JpaRepository<OutletProduct, String > {
    @Query(nativeQuery = true,
            value = "SELECT id, qty, product_id, outlet_id, qty FROM m_outlet_product WHERE product_id=:productId AND outlet_id=:outletId")
    Optional<OutletProduct> findOutletProductByProductIdAndOutletId(@Param("productId") String productId,
                                                                    @Param("outletId") String outletId);

    @Query(nativeQuery = true,
    value = """
            SELECT op.id, op.qty, op.product_id, op.outlet_id, op.qty FROM m_outlet_product AS op
            INNER JOIN m_product AS p ON p.id = op.product_id
            INNER JOIN m_supplier AS s ON s.id = p.supplier_id
            WHERE s.id LIKE '%:supplierId%' AND op.outlet_id LIKE '%:outletId%'
            """)
    Optional<List<OutletProduct>> findListOutletProductByProductIdAndOutletId(@Param("supplierId") String supplierId,
                                                                              @Param("outletId") String outletId);
}
