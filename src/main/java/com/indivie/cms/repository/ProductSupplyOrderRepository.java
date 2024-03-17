package com.indivie.cms.repository;

import com.indivie.cms.entity.ProductSupplyOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSupplyOrderRepository extends JpaRepository<ProductSupplyOrder, String> {
    @Query(nativeQuery = true,
    value = """
            SELECT pso.id, pso.order_date, pso.receiving_outlet_id FROM t_product_supply_order AS pso
            INNER JOIN m_outlet AS o ON o.id = pso.receiving_outlet_id
            INNER JOIN t_product_supply_order_detail AS psod ON psod.product_supply_order_id = pso.id
            INNER JOIN m_product AS p ON p.id = psod.product_id
            WHERE pso.receiving_outlet_id LIKE :outletId
            AND psod.product_id LIKE :productId
            AND p.supplier_id LIKE :supplierId
            """)
    public Optional<List<ProductSupplyOrder>> findAllFiltered(@Param("outletId") String outletId,
                                                              @Param("productId") String productId,
                                                              @Param("supplierId") String supplierId);
}
